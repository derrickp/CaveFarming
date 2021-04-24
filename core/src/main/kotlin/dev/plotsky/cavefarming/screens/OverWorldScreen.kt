package dev.plotsky.cavefarming.screens

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.maps.MapLayer
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.math.Polyline
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.viewport.FitViewport
import dev.plotsky.cavefarming.CaveFarming
import dev.plotsky.cavefarming.GAME_HEIGHT
import dev.plotsky.cavefarming.GAME_WIDTH
import dev.plotsky.cavefarming.assets.TextureAtlasAssets
import dev.plotsky.cavefarming.assets.get
import dev.plotsky.cavefarming.components.Box2DComponent
import dev.plotsky.cavefarming.components.CharacterComponent
import dev.plotsky.cavefarming.components.InteractComponent
import dev.plotsky.cavefarming.components.InventoryComponent
import dev.plotsky.cavefarming.components.MoveComponent
import dev.plotsky.cavefarming.components.NameComponent
import dev.plotsky.cavefarming.components.RenderComponent
import dev.plotsky.cavefarming.components.TransformComponent
import dev.plotsky.cavefarming.systems.ActionsSystem
import dev.plotsky.cavefarming.systems.Box2DSystem
import dev.plotsky.cavefarming.systems.CameraMoveSystem
import dev.plotsky.cavefarming.systems.GrowthSystem
import dev.plotsky.cavefarming.systems.MoveSystem
import dev.plotsky.cavefarming.systems.PlayerControlSystem
import dev.plotsky.cavefarming.systems.RenderSystem
import ktx.app.KtxScreen
import ktx.ashley.entity
import ktx.ashley.with
import ktx.box2d.BodyDefinition
import ktx.box2d.body
import ktx.box2d.box
import ktx.box2d.chain
import ktx.box2d.loop
import ktx.tiled.forEachLayer
import ktx.tiled.isEmpty
import ktx.tiled.shape

class OverWorldScreen(
    private val caveFarming: CaveFarming,
    private val batch: Batch,
    private val engine: PooledEngine,
    private val assetManager: AssetManager
) : KtxScreen {
    private val viewport = FitViewport(GAME_WIDTH, GAME_HEIGHT)
    private val world = World(Vector2.Zero, true).apply {
        autoClearForces = false
    }
    private val unitScale = ONE / SCALE_DENOMINATOR
    private val map: TiledMap by lazy {
        assetManager.get("first_cave.tmx")
    }
    private val renderer by lazy {
        OrthogonalTiledMapRenderer(map, unitScale)
    }

    override fun dispose() {
        world.dispose()
        super.dispose()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
        super.resize(width, height)
    }
    override fun render(delta: Float) {
        viewport.camera.position.set(GAME_WIDTH / TWO, GAME_HEIGHT / TWO, ZERO.toFloat())
        if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            removeSystems()
            caveFarming.setScreen<InventoryScreen>()
        }

        // everything is now done within our entity engine --> update it every frame
        engine.update(delta)
    }

    override fun show() {
        // initialize entity engine
        if (engine.entities.size() == ZERO) {
            addMainCharacter()
        }

        if (engine.systems.size() == ZERO) {
            addSystems()
        }

        parseMapLayers()

        super.show()
    }

    private fun addMainCharacter() {
        engine.entity {
            with<Box2DComponent> {
                body = world.body(BodyDef.BodyType.DynamicBody) {
                    position.set(GAME_WIDTH / TWO + ONE_QUARTER * HALF, GAME_HEIGHT / TWO + ONE_QUARTER * HALF)
                    fixedRotation = false
                    allowSleep = true

                    box(
                        ONE_QUARTER,
                        ONE_QUARTER,
                        Box2DComponent.TMP_VECTOR2.set(ZERO.toFloat(), -ONE_QUARTER * HALF + ONE_QUARTER * HALF)
                    ) {
                        friction = ZERO.toFloat()
                        isSensor = false
                    }

                    val optionalInit: BodyDefinition.() -> Unit = {}

                    this.apply(optionalInit)

//                    userData = this@withBox2DComponents.entity
                }
            }
            with<InteractComponent>()
            with<NameComponent> { name = "goblin" }
            with<MoveComponent>()
            with<TransformComponent> {
                position.set(
                    GAME_WIDTH / TWO,
                    GAME_HEIGHT / TWO,
                    0f
                )
                size.set(ONE_QUARTER, ONE_QUARTER)
            }
            with<InventoryComponent>()
            with<RenderComponent> {
                sprite.setRegion(assetManager[TextureAtlasAssets.CaveFarming].findRegion("goblin_big_hat"))
                z = CHARACTER_Z_LEVEL
            }
            with<CharacterComponent>()
        }
    }

    private fun removeSystems() {
        engine.removeAllSystems()
    }

    private fun addSystems() {
        engine.apply {
            addSystem(PlayerControlSystem())
            addSystem(MoveSystem())
            addSystem(Box2DSystem(world))
            addSystem(CameraMoveSystem(viewport))
            addSystem(ActionsSystem(engine, assetManager))
            addSystem(GrowthSystem(assetManager))
            addSystem(RenderSystem(batch, viewport, renderer))
//            addSystem(RenderSystem(hole, batch, font, camera))
            // add CollisionSystem last as it removes entities and this should always
            // happen at the end of an engine update frame
//            addSystem(CollisionSystem(hole, assets))
//            addSystem(AnimationSystem(batch, font, camera))
        }
    }

    private fun parseMapLayers() {
        map.forEachLayer<MapLayer> {
            createCollisionBody(it)
        }
    }

    private fun createCollisionBody(layer: MapLayer) {
        val objects = layer.objects
        if (objects.isEmpty()) {
            return
        }

        world.body(BodyDef.BodyType.StaticBody) {
            fixedRotation = true

            objects.forEach { mapObject ->
                when (val shape = mapObject.shape) {
                    is Polyline -> {
                        shape.setPosition(shape.x * unitScale, shape.y * unitScale)
                        shape.setScale(unitScale, unitScale)
                        chain(shape.transformedVertices)
                    }
                    is Polygon -> {
                        shape.setPosition(shape.x * unitScale, shape.y * unitScale)
                        shape.setScale(unitScale, unitScale)
                        loop(shape.transformedVertices)
                    }
                    is Rectangle -> {
                        val x = shape.x * unitScale
                        val y = shape.y * unitScale
                        val width = shape.width * unitScale
                        val height = shape.height * unitScale

                        if (width <= ZERO.toFloat() || height <= ZERO.toFloat()) {
                            return@forEach
                        }

                        // define loop vertices
                        // bottom left corner
                        TMP_RECTANGLE_VERTICES[BOTTOM_LEFT_X] = x
                        TMP_RECTANGLE_VERTICES[BOTTOM_LEFT_Y] = y
                        // top left corner
                        TMP_RECTANGLE_VERTICES[TOP_LEFT_X] = x
                        TMP_RECTANGLE_VERTICES[TOP_LEFT_Y] = y + height
                        // top right corner
                        TMP_RECTANGLE_VERTICES[TOP_RIGHT_X] = x + width
                        TMP_RECTANGLE_VERTICES[TOP_RIGHT_Y] = y + height
                        // bottom right corner
                        TMP_RECTANGLE_VERTICES[BOTTOM_RIGHT_X] = x + width
                        TMP_RECTANGLE_VERTICES[BOTTOM_RIGHT_Y] = y

                        loop(TMP_RECTANGLE_VERTICES)
                    }
                }
            }
        }
    }

    companion object {
        private val TMP_RECTANGLE_VERTICES = FloatArray(8)
        private const val ONE = 1f
        private const val TWO = 2f
        private const val ZERO = 0
        private const val HALF = 0.5f
        private const val ONE_QUARTER = 1.25f
        private const val SCALE_DENOMINATOR = 32f
        private const val CHARACTER_Z_LEVEL = 10

        private const val BOTTOM_LEFT_X = 0
        private const val BOTTOM_LEFT_Y = 1
        private const val TOP_LEFT_X = 2
        private const val TOP_LEFT_Y = 3
        private const val TOP_RIGHT_X = 4
        private const val TOP_RIGHT_Y = 5
        private const val BOTTOM_RIGHT_X = 6
        private const val BOTTOM_RIGHT_Y = 7
    }
}
