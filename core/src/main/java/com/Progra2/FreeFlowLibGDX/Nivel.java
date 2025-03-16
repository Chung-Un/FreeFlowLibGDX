/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Progra2.FreeFlowLibGDX;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Nadiesda Fuentes
 */
public abstract class Nivel implements InputProcessor{
    protected Jugador jugador;
    protected int sizeGrid;
    protected double tiempoRestante;
    protected boolean nivelCompletado;
    protected Punto[][] gridPuntos;
    protected int[][] grid;
    protected ArrayList<Punto> puntos;
    protected static ArrayList<Conexion> conexiones;
    private Thread hiloTiempo, hiloColisiones;
    private boolean verificandoConexiones;
    private float y;
    protected FreeFlow FlowFree;
    protected Stage stage;
    private volatile boolean tiempoCorriendo, colisionDetectada, corriendoHiloColision;//volatile: sera modificado por threads
    protected boolean mostrarMensajeCompletacion;
    protected ShapeRenderer renderer;
    private float offSetX, offSetY;
    private float sizeCelda;
    protected SpriteBatch batch;
    protected Texture texturaPink,texturaCyan,texturaOrange,texturaYellow,texturaGreen,texturaPurple, texturaRed,
    texturaBlue, texturaSalmon;
    private int dotInicialX, dotInicialY;
    private boolean isDragging;
    private List<int []> pathActual;
    protected ImageButton btnRegresar, btnReiniciar;
    private Punto puntoSeleccionado;
    protected Label labelTimer;
    private Skin skin;
    private BitmapFont font;
    private Texture texturaRegresar, texturaRestart;
    private Table table;
    protected int numeroNivel;
    protected double tiempoLimite;
    private InputMultiplexer multipleInput;
    private int dotFinalX, dotFinalY; 

    public Nivel(int sizeGrid, double tiempoLimite, Jugador jugador, FreeFlow FlowFree, int sizeCelda ){
        this.sizeGrid = sizeGrid;  
        this.tiempoRestante = tiempoLimite;
        this.nivelCompletado = false;
        this.FlowFree = FlowFree;
        gridPuntos = new Punto[sizeGrid][sizeGrid];
        texturaPink = new Texture("pinkdot.png");
        texturaCyan = new Texture("cyandot.png");
        texturaOrange = new Texture("orangedot.png");
        texturaYellow = new Texture("yellowdot.png");
        texturaGreen = new Texture("greendot.png");
        texturaPurple = new Texture("purpledot.png");
        texturaRed = new Texture("reddot.png");
        texturaBlue = new Texture("bluedot.png");
        texturaSalmon = new Texture("salmondot.png");
        puntos = new ArrayList();
        this.conexiones = new ArrayList();
        this.verificandoConexiones = false;
        this.jugador = jugador;
        stage = new Stage(new ScreenViewport());
        multipleInput =new InputMultiplexer();
        multipleInput.addProcessor(this);
        multipleInput.addProcessor(stage);
        Gdx.input.setInputProcessor(multipleInput);
        mostrarMensajeCompletacion = false;
        renderer = new ShapeRenderer();
        batch = new SpriteBatch();
        this.sizeCelda = sizeCelda;
        this.FlowFree = FlowFree;
        this.tiempoLimite = tiempoLimite;
        texturaRegresar = new Texture("botonBack.png");
        texturaRestart = new Texture("restartbtn.png");
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        font = new BitmapFont();
        skin.add("default", font);
        numeroNivel=0;
        
        labelTimer = new Label("Tiempo: " + (int)(tiempoLimite- tiempoRestante) , skin);
        labelTimer.setPosition(10, Gdx.graphics.getHeight() - 30);
        stage.addActor(labelTimer);

        ImageButton.ImageButtonStyle btnRegresarStyle = new ImageButton.ImageButtonStyle();
        btnRegresarStyle.imageUp = new TextureRegionDrawable(new TextureRegion(texturaRegresar));
        btnRegresar = new ImageButton(btnRegresarStyle);
        
        ImageButton.ImageButtonStyle btnRestartStyle = new ImageButton.ImageButtonStyle();
        btnRestartStyle.imageUp = new TextureRegionDrawable(new TextureRegion(texturaRestart));
        btnReiniciar = new ImageButton(btnRestartStyle);
        
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        table.bottom();
        table.add(btnRegresar).size(70).pad(10).expandX().left();
        table.add(btnReiniciar).size(70).pad(10).expandX().right();
        table.layout();
        
        btnRegresar.addListener(new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            System.out.println("btnRegresar pressed");
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int resultado = JOptionPane.showConfirmDialog(null, "Esta seguro que desea regresar al mapa?",
                    "Confirmacion", dialogButton);

            if (resultado == JOptionPane.YES_OPTION) {
                FlowFree.setScreen(new PantallaMapa(FlowFree, jugador));
                disposeNivel();
            }
        }
         });

        btnReiniciar.addListener(new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            System.out.println("btnReiniciar pressed");
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int resultado = JOptionPane.showConfirmDialog(null, "Esta seguro que desea reiniciar el nivel?",
                    "Confirmacion", dialogButton);

            if (resultado == JOptionPane.YES_OPTION) {
                PantallaJuego.manejoNivel.reiniciarNivel();
            }
        }
        });
    }
    
    public abstract void inicializar();
    
    public void actualizar(float delta) {
    if (nivelCompletado) {
        return;
    }

    if (tiempoRestante <= 0) {
        System.out.println("Forzando actualización de tiempo a 0...");
        tiempoRestante = 0; 

        labelTimer.setText("Tiempo: 0");
        
         Gdx.app.postRunnable(() -> {
             System.out.println("Mostrando mensaje de time is up");
            JOptionPane.showMessageDialog(null, "Su tiempo ha acabado...", "Fail", JOptionPane.INFORMATION_MESSAGE);
             System.out.println("Usuario ya sabe que su tiemp se acabo");
            PantallaJuego.manejoNivel.getNivelActual().reiniciarNivel();
        });

        return;
    }

    labelTimer.setText("Tiempo: " + (int) tiempoRestante);

    if (todosPuntosConectados()) {
        System.out.println("todos puntos conectados? ");
        imprimirGrid();
        nivelCompletado = true;
        mostrarMensajeCompletacion = true;
        detenerHiloTiempo();
        jugador.nivelesCompletados++; 
        jugador.tiemposPorNivel.set(numeroNivel, (tiempoLimite - tiempoRestante));
    }
}


    
    public  void dibujar(){
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.WHITE); //dibujar el grid
        
        for (int i = 0 ; i<= sizeGrid ; i++){
            //lineas verticales
            renderer.rectLine(offSetX + i * sizeCelda, offSetY, // punto inicial
            offSetX + i * sizeCelda, offSetY + sizeCelda * sizeGrid, // punto final 
            2 );
            //lineas horizaontales
            renderer.rectLine(offSetX, offSetY + i * sizeCelda, // punto inicial
            offSetX + sizeCelda * sizeGrid, offSetY + i * sizeCelda, // punto final 
            2);
        }
        renderer.end();
        
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        //para conexiones
        for (Conexion conexion : conexiones) {
            if (conexion == PantallaJuego.conexionHovered) {
            renderer.setColor(Color.GRAY); // Change color for hovered conexion
        } else {
            renderer.setColor(conexion.getColor());
        }
            
            List<int[]> path = conexion.getPath();
            for (int i = 1; i < path.size(); i++) {
                int[] inicio = path.get(i - 1);
                int[] fin = path.get(i);

                // Convert grid coordinates to screen coordinates
                float inicioX = offSetX + inicio[0] * sizeCelda + sizeCelda / 2;
                float inicioY = offSetY + (sizeGrid - 1 - inicio[1]) * sizeCelda + sizeCelda / 2;
                float finX = offSetX + fin[0] * sizeCelda + sizeCelda / 2;
                float finY = offSetY + (sizeGrid - 1 - fin[1]) * sizeCelda + sizeCelda / 2;

                // Draw the line
                renderer.rectLine(inicioX, inicioY, finX, finY, 10);
            }
        }
        renderer.end();
        
        //para dots
        batch.begin();
        for (Punto punto : puntos) {
        float x = offSetX + punto.getCol() * sizeCelda;
        float y = offSetY + (sizeCelda * (sizeGrid - 1 - punto.getFila()));

        Texture texturaDot = getTexturaDot(punto.getColor());
        batch.draw(texturaDot, x, y, sizeCelda, sizeCelda);
        }
        batch.end();
        
        //DEBUG
         renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.RED);
        for (Punto punto : puntos) {
            float x = offSetX + punto.getCol() * sizeCelda;
            float y = offSetY + (sizeCelda * (sizeGrid - 1 - punto.getFila()));
            renderer.rect(x, y, sizeCelda, sizeCelda); // Draw a rectangle around the dot
        }
        renderer.end();
       
        //para el timer
        batch.begin();
        stage.act(Gdx.graphics.getDeltaTime()); 
        stage.draw();
        batch.end();
       
        if (mostrarMensajeCompletacion) {
            mostrarMensajeCompletacion = false;
            Gdx.app.postRunnable(() -> {
            JOptionPane.showMessageDialog(null, "Nivel completado con un tiempo de " + (int) (tiempoLimite - tiempoRestante )+
                    " segundos", "Completacion", JOptionPane.INFORMATION_MESSAGE);
            PantallaJuego.manejoNivel.getNivelActual();
            disposeNivel();
            FlowFree.setScreen(new PantallaMapa(FlowFree, jugador)); 
    });
    }
    }
    
    public  boolean verificarCompletacion(){
        return nivelCompletado;

    }
    
    public void reiniciarNivel(){
        this.grid = new int[PantallaJuego.manejoNivel.getNivelActual().sizeGrid][PantallaJuego.manejoNivel.getNivelActual().sizeGrid];
        tiempoRestante = tiempoLimite;
        nivelCompletado = false;
        puntos.clear();
        conexiones.clear();
        detenerHiloTiempo(); 
        detenerHiloColisiones();
        inicializar(); 
        this.calcularOffsets();
    }
    
    public abstract void disposeNivel();
    
    protected boolean todosPuntosConectados() {
        for (Punto punto : puntos) {
            if (!punto.estaConectado()) {
                return false;
            }
        }
        
        for(int i = 0; i<PantallaJuego.manejoNivel.getNivelActual().sizeGrid ; i++){
            for( int x =0; x< PantallaJuego.manejoNivel.getNivelActual().sizeGrid ; x++){
                if (PantallaJuego.manejoNivel.getNivelActual().grid[i][x] !=2){
                    return false;
                }
            }
        }
        return true;
    }
    
    protected void iniciarHiloTiempo() {
    detenerHiloTiempo();

    System.out.println("Hilo tiempo iniciado");
    tiempoCorriendo = true; 

    Timer.schedule(new Timer.Task() {
        @Override
        public void run() {
            if (!tiempoCorriendo || tiempoRestante <= 0 || nivelCompletado) {
                cancel(); 
                return;
            }

            tiempoRestante--; 
            System.out.println("Tiempo restante: " + tiempoRestante);

            labelTimer.setText("Tiempo: " + tiempoRestante);

            if (tiempoRestante <= 0) {
                System.out.println("Tiempo agotado!");
                detenerHiloTiempo();
            }
        }
    }, 0, 1); 
}


protected void detenerHiloTiempo() {
    tiempoCorriendo = false; 
    Timer.instance().clear(); 
}
    
    protected void iniciarHiloColisiones(){
        hiloColisiones = new Thread(()->{
            while (corriendoHiloColision){
                if(isDragging && pathActual!=null){
                    colisionDetectada = verificarColisiones(pathActual);
                    if(colisionDetectada){
                        System.out.println("Colision detectada");
                    } 
                }
                try{
                    Thread.sleep(50);
                } catch ( InterruptedException e){
                    System.out.println("Hilo de colisiones interrumpido");
                    break;
                }
            }
        });
        hiloColisiones.start();
    
    }
    
    protected void detenerHiloColisiones(){
        corriendoHiloColision = false;
        if(hiloColisiones!= null && hiloColisiones.isAlive()){
            hiloColisiones.interrupt();
        }
        hiloColisiones=null;
    }
    
    private boolean verificarColisiones(List<int[]> pathActual) {
    if(pathActual ==null){
        return false;
    }
    for (Conexion conexion : conexiones) {
        for (int[] posicion : conexion.getPath()) {
            for (int[] posicionActual : pathActual) {
                if (posicionActual[0] == posicion[0] && posicionActual[1] == posicion[1]) {
                    return true; 
                }
            }
        }
    }

    
    for (int[] posicion : pathActual) {
        int fila = posicion[1];
        int col = posicion[0];

        //skip las dots iniciales y finales del path actual
        if ((fila == dotInicialY && col == dotInicialX) || (fila == dotFinalY && col == dotFinalX)) {
            continue;
        }

        //colision con dots
        if (grid[fila][col] == 1) {
            Punto punto = gridPuntos[fila][col];
            if (punto != null && !punto.getColor().equals(gridPuntos[dotInicialY][dotInicialX].getColor())) {
                return true; 
            }
        }

        //colision entre paths existentes
        if (fila < 0 || fila >= sizeGrid || col < 0 || col >= sizeGrid || grid[fila][col] == 2) {
            return true; 
        }
    }

    return false; 
}
    
    public void registrarCompletado(int nivelJugado){
        if(nivelCompletado){
            int tiempoJugado = (int) tiempoRestante;
        }
    }
    
    public Stage getStage(){
        return stage;
    }
    
    public Texture getTexturaDot(Color color){
        if (color.equals(Color.PINK)) return texturaPink;
        if (color.equals(Color.ORANGE)) return texturaOrange;
        if (color.equals(Color.CYAN)) return texturaCyan;
        if (color.equals(Color.GREEN)) return texturaGreen;
        if (color.equals(Color.YELLOW)) return texturaYellow;
        if(color.equals(Color.PURPLE)) return texturaPurple;
        if(color.equals(Color.RED)) return texturaRed;
        if(color.equals(Color.BLUE)) return texturaBlue;
        if(color.equals(Color.SALMON))return texturaSalmon;
        return null;
    }
    
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        
        System.out.println("Touch down size grid: " + PantallaJuego.manejoNivel.getNivelActual().sizeGrid);
        System.out.println("size celda actual : " + PantallaJuego.manejoNivel.getNivelActual().sizeCelda +  "offsetX y offsety" + offSetX + "," + offSetY);
        int sizeGridActual = PantallaJuego.manejoNivel.getNivelActual().sizeGrid;
        
        Vector2 posicionTocada = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);
        
        int col = (int) ((posicionTocada.x - PantallaJuego.manejoNivel.getNivelActual().offSetX) / PantallaJuego.manejoNivel.getNivelActual().sizeCelda);
        int fila = sizeGridActual - 1 - (int) ((posicionTocada.y - PantallaJuego.manejoNivel.getNivelActual().offSetY) / 
                PantallaJuego.manejoNivel.getNivelActual().sizeCelda);

        System.out.println("Touched screen: (" + screenX + ", " + screenY + ")");
        System.out.println("Touched grid: (" + fila + ", " + col + ")"); // Debug
               
        
        if ((col >= 0 && col < sizeGridActual) && (fila >= 0 && fila < sizeGridActual)) { //si esta en bounds
            Conexion conexionClicked = getConexionSeleccionada(col, fila);
            if (conexionClicked != null) {
            desconectarDots(conexionClicked);
            return true;
        }
            if (PantallaJuego.manejoNivel.getNivelActual().grid[fila][col] == 1) { // si la celda contiene un dot
                System.out.println("Dot seleccionada");
                dotInicialX = col;
                dotInicialY = fila;
                dotFinalX = col;
                dotFinalY = fila;
                isDragging = true;
                PantallaJuego.manejoNivel.getNivelActual().pathActual = new ArrayList<>();
                PantallaJuego.manejoNivel.getNivelActual().pathActual.add(new int[]{dotInicialX, dotInicialY}); // Add the starting position
                System.out.println("Path inicializado");
                return true;
            }
        }

        return false;

    }

   @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
    int sizeGridActual = PantallaJuego.manejoNivel.getNivelActual().sizeGrid;
    if (isDragging) {
        if (PantallaJuego.manejoNivel.getNivelActual().pathActual == null) {
            //si esta null inicializamos
            PantallaJuego.manejoNivel.getNivelActual().pathActual = new ArrayList<>();
        }

        Vector2 posicionTocada = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);
        int col = (int) ((posicionTocada.x - PantallaJuego.manejoNivel.getNivelActual().offSetX) / 
                PantallaJuego.manejoNivel.getNivelActual().sizeCelda);
        int fila = sizeGridActual - 1 - (int) ((posicionTocada.y - PantallaJuego.manejoNivel.getNivelActual().offSetY) / 
                PantallaJuego.manejoNivel.getNivelActual().sizeCelda);

        if ((col >= 0 && col < sizeGridActual) && (fila >= 0 && fila < sizeGridActual)) {
            //si esta en bounds
            if (!PantallaJuego.manejoNivel.getNivelActual().pathActual.isEmpty()) {
                //si el path esa vacio
                int[] posicionAnterior = PantallaJuego.manejoNivel.getNivelActual().pathActual.get(
                        PantallaJuego.manejoNivel.getNivelActual().pathActual.size() - 1);

                boolean isHorizontal = (fila == posicionAnterior[1] && Math.abs(col - posicionAnterior[0]) == 1);
                boolean isVertical = (col == posicionAnterior[0] && Math.abs(fila - posicionAnterior[1]) == 1);

                if (isHorizontal || isVertical) {
                    //si no es un movimiento diagonal
                    List<int[]> tempPath = new ArrayList<>(PantallaJuego.manejoNivel.getNivelActual().pathActual);
                    tempPath.add(new int[]{col, fila});
                    if (!verificarColisiones(tempPath)) {
                        PantallaJuego.manejoNivel.getNivelActual().pathActual.add(new int[]{col, fila});
                    } else {
                        System.out.println("Colisión detectada, no se puede dibujar aquí");
                        colisionDetectada = true;
                        return true;
                    }
                } else {
                    System.out.println("Movimiento diagonal no permitido");
                }
            } else {
                PantallaJuego.manejoNivel.getNivelActual().pathActual.add(new int[]{col, fila});
            }
        }
        return true;
    }
    return false;
}
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) { 
    PantallaJuego.conexionHovered = null; // Clear hover state
    System.out.println("Touch up");
    
    
    imprimirGrid();
    if (isDragging) {
        System.out.println("Is dragging");
        if(colisionDetectada){
            System.out.println("Colision AAAA");
            isDragging = false;
            PantallaJuego.manejoNivel.getNivelActual().pathActual = null;
            colisionDetectada = false;
            return true;
        }
        int sizeGridActual = PantallaJuego.manejoNivel.getNivelActual().sizeGrid;
        Vector2 posicionTocada = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);
        int col = (int) ((posicionTocada.x - PantallaJuego.manejoNivel.getNivelActual().offSetX) / 
                PantallaJuego.manejoNivel.getNivelActual().sizeCelda); 
        int fila = sizeGridActual - 1 - (int) ((posicionTocada.y - PantallaJuego.manejoNivel.getNivelActual().offSetY) / 
                PantallaJuego.manejoNivel.getNivelActual().sizeCelda);

        if (col >= 0 && col < sizeGridActual && fila >= 0 && fila < sizeGridActual) { 
            Punto dotInicial = PantallaJuego.manejoNivel.getNivelActual().gridPuntos[dotInicialY][dotInicialX];
            Punto dotFinal = PantallaJuego.manejoNivel.getNivelActual().gridPuntos[fila][col];

            if (dotFinal != null && dotFinal.getColor().equals(dotInicial.getColor())) {
                if (PantallaJuego.manejoNivel.getNivelActual().pathActual != null &&
                        !PantallaJuego.manejoNivel.getNivelActual().pathActual.isEmpty() &&
                        esPathValido(PantallaJuego.manejoNivel.getNivelActual().pathActual)) {
                    System.out.println("Path valido");
                    for (int[] posicion : PantallaJuego.manejoNivel.getNivelActual().pathActual) {
                        PantallaJuego.manejoNivel.getNivelActual().grid[posicion[1]][posicion[0]] = 2; 
                        System.out.println("Marcado en grid: " + posicion[1] + "," + posicion[0]);
                    }
                    
                    PantallaJuego.manejoNivel.getNivelActual().conexiones.add(new Conexion(dotInicial, dotFinal,
                    PantallaJuego.manejoNivel.getNivelActual().pathActual));
                    dotInicial.conectado = true;
                    dotFinal.conectado = true;
                    System.out.println("Puntos " + dotFinal.getColor() + " conectados");
                    System.out.println("Path: " + PantallaJuego.manejoNivel.getNivelActual().pathActual);
                } else {
                    
                    System.out.println("Path no es valido");
                }
            }
        }
        isDragging = false;
        PantallaJuego.manejoNivel.getNivelActual().pathActual = null; // Ensure pathActual is cleared at the end
        imprimirGrid();
        return true;
    }
    return false;
}

    
    public boolean esPathValido(List<int[]> pathActual) {
        for (int[] posicion : pathActual) {
        int fila = posicion[1];
        int col = posicion[0];
        
        if (fila < 0 || fila >= PantallaJuego.manejoNivel.getNivelActual().sizeGrid ||
            col < 0 || col >= PantallaJuego.manejoNivel.getNivelActual().sizeGrid ||
            grid[fila][col] == 2) {
            System.out.println("Path no es valido porque hay un 2 en " + fila + col);
            return false;
        }
    }
    return true; 
    }
    
   public void seleccionDots(int fila, int col, Color color) {
        if (puntoSeleccionado == null) { //primer dot seleccionado
            puntoSeleccionado = new Punto(fila, col, color);
            System.out.println("Seleccionado dot en row(" + fila + ") y col{" + col + ")");
        } else {
            if(puntoSeleccionado.getColor().equals(color)){
            System.out.println("Intenta conectar");
            puntoSeleccionado = null;
            }
            else{
            System.out.println("Colores no son iguales");
            }
            puntoSeleccionado = null;
        }
    }
   
   public void imprimirGrid(){
       for (int i = 0; i < PantallaJuego.manejoNivel.getNivelActual().sizeGrid; i++) {
        for (int j = 0; j < PantallaJuego.manejoNivel.getNivelActual().sizeGrid; j++) {
            System.out.print(PantallaJuego.manejoNivel.getNivelActual().grid[i][j] + " ");
        }
        System.out.println();
        }
   }
   
   public Conexion getConexionSeleccionada(int col, int fila){
       System.out.println("Buscando conexion seleccionada...");
       
       if (PantallaJuego.manejoNivel.getNivelActual().conexiones.isEmpty()){
           System.out.println("estoy vacia");
       }
       for (Conexion conexion : PantallaJuego.manejoNivel.getNivelActual().conexiones){
           System.out.println("entro, permiso");
          for( int[] posicion : conexion.getPath()){
              System.out.println("yo tambien, excuse me");
              if( posicion[1]== fila && posicion[0] == col){
                  System.out.println("Conexion seleccionada ");
                  return conexion;
              }
          }
       }
       return null;
   }

   private void desconectarDots(Conexion conexion){
       PantallaJuego.manejoNivel.getNivelActual().conexiones.remove(conexion);
       Punto punto1 = conexion.getPunto1();
       punto1.conectado = false;
       Punto punto2 = conexion.getPunto2();
       punto2.conectado = false;
       
       for (int[] posicion : conexion.getPath()){
           PantallaJuego.manejoNivel.getNivelActual().grid[posicion[1]][posicion[0]] =0;
           
       }
       
       PantallaJuego.manejoNivel.getNivelActual().grid[punto1.getFila()][punto1.getCol()] =1;
       PantallaJuego.manejoNivel.getNivelActual().grid[punto2.getFila()][punto2.getCol()]=1;
       
       System.out.println("Despues de eliminar la conexion");
       imprimirGrid();
       System.out.println("Se elimino la conexion");
   }
   
   public static Conexion conexionHovered( int screenX, int screenY){
       int sizeGridActual = PantallaJuego.manejoNivel.getNivelActual().sizeGrid;
       Vector2 posicionTocada = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);
       
       int col = (int) ((posicionTocada.x - PantallaJuego.manejoNivel.getNivelActual().offSetX) / PantallaJuego.manejoNivel.getNivelActual().sizeCelda);
       int fila = sizeGridActual - 1 - (int) ((posicionTocada.y - PantallaJuego.manejoNivel.getNivelActual().offSetY) / PantallaJuego.manejoNivel.getNivelActual().sizeCelda);
       
       if (col >= 0 && col < sizeGridActual && fila >= 0 && fila < sizeGridActual) {
        for (Conexion conexion : conexiones) {
            for (int[] posicion : conexion.getPath()) {
                if (posicion[0] == col && posicion[1] == fila) {
                    return conexion;
                }
            }
        }
    }
    return null;
   }
   
   protected void calcularOffsets() {
    offSetX = (Gdx.graphics.getWidth() - PantallaJuego.manejoNivel.getNivelActual().sizeCelda * sizeGrid) / 2;
    offSetY = (Gdx.graphics.getHeight() -PantallaJuego.manejoNivel.getNivelActual().sizeCelda * sizeGrid) / 2;
        System.out.println("**CALCULAR OFFSETS**\nsizeCelda: " + PantallaJuego.manejoNivel.getNivelActual().sizeCelda + ", offSetX: " + offSetX + ", offSetY: " + offSetY);

}
}
