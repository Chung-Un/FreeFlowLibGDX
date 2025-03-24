/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Progra2.FreeFlowLibGDX;


import com.Progra2.flowfree.flowfreegame.FlowFreeGame;
import static com.Progra2.flowfree.flowfreegame.LanguageManager.languageManager;
import com.Progra2.flowfree.model.Usuario;
import com.Progra2.flowfree.screens.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
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
import java.util.HashSet;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Nadiesda Fuentes
 */
public abstract class Nivel implements InputProcessor{
    protected Usuario jugador;
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
    protected FlowFreeGame FlowFree;
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
    protected List<int []> pathActual;
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
    public static Music musicNivel;
    public boolean todosNivelesCompletados;
    private long tiempoJugado;
    protected HashSet<Par> celdasOcupadas;

    public Nivel(int sizeGrid, double tiempoLimite, Usuario jugador, FlowFreeGame FlowFree, int sizeCelda ){
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
        conexiones = new ArrayList();
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
        todosNivelesCompletados = false;
        celdasOcupadas = new HashSet<>();
        
        labelTimer = new Label(languageManager.getText("tiempo_restante") + (int)(tiempoLimite- tiempoRestante) , skin);
        labelTimer.setPosition(10, Gdx.graphics.getHeight() - 30);
        stage.addActor(labelTimer);

        musicNivel = Gdx.audio.newMusic(Gdx.files.internal("musicnivel.mp3"));
        musicNivel.setVolume(jugador.getVolumenMusica());
       
        
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
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int resultado = JOptionPane.showConfirmDialog(null, languageManager.getText("seguro_regresar"),
                    languageManager.getText("confirmacion"), dialogButton);

            if (resultado == JOptionPane.YES_OPTION) {
                FlowFree.setScreen(new PantallaMapa(FlowFree, jugador));
                disposeNivel();
            }
        }
         });

        btnReiniciar.addListener(new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int resultado = JOptionPane.showConfirmDialog(null, languageManager.getText("seguro_reiniciar"),
                    languageManager.getText("confirmacion"), dialogButton);

            if (resultado == JOptionPane.YES_OPTION) {
                PantallaJuego.manejoNivel.reiniciarNivel();
            }
        }
        });
    }
    
    public abstract void inicializar();
    
    public void actualizar(float delta) {
    
    tiempoJugado = (long)(tiempoLimite - tiempoRestante);

    if (nivelCompletado) {
        return;
    }

    if (tiempoRestante <= 0) {
        tiempoRestante = 0; 

        labelTimer.setText(languageManager.getText("tiempo_restante")+"0");
        
         Gdx.app.postRunnable(() -> {
            JOptionPane.showMessageDialog(null, languageManager.getText("acabo_tiempo"), "Fail", JOptionPane.INFORMATION_MESSAGE);
            jugador.actualizarEstadisticas(0, tiempoJugado);
            PantallaJuego.manejoNivel.getNivelActual().reiniciarNivel();
        });

        return;
    }

    labelTimer.setText("Tiempo: " + (int) tiempoRestante);

    if (todosPuntosConectados()) {
        nivelCompletado = true;
        mostrarMensajeCompletacion = true;
        detenerHiloTiempo();
        if(jugador.nivelesCompletados<5 && PantallaJuego.manejoNivel.getNivelActual().numeroNivel > jugador.nivelesCompletados){
            jugador.actualizarEstadisticas(1, tiempoJugado);
        } else if(jugador.nivelesCompletados>5){
            jugador.actualizarEstadisticas(0, tiempoJugado);
        }
        jugador.tiemposPorNivel.set(numeroNivel-1, (tiempoLimite - tiempoRestante));
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
            renderer.setColor(Color.GRAY); //cambiamos el color de la conexion hovered
        } else {
            renderer.setColor(conexion.getColor());
        }
            
            List<int[]> path = conexion.getPath();
            for (int i = 1; i < path.size(); i++) {
                int[] inicio = path.get(i - 1);
                int[] fin = path.get(i);

                float inicioX = offSetX + inicio[0] * sizeCelda + sizeCelda / 2;
                float inicioY = offSetY + (sizeGrid - 1 - inicio[1]) * sizeCelda + sizeCelda / 2;
                float finX = offSetX + fin[0] * sizeCelda + sizeCelda / 2;
                float finY = offSetY + (sizeGrid - 1 - fin[1]) * sizeCelda + sizeCelda / 2;

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
        

        //para el timer
        batch.begin();
        stage.act(Gdx.graphics.getDeltaTime()); 
        stage.draw();
        batch.end();
       
        if (mostrarMensajeCompletacion) {
            mostrarMensajeCompletacion = false;
            Gdx.app.postRunnable(() -> {
            JOptionPane.showMessageDialog(null, languageManager.getText("completo_nivel") + (int) (tiempoLimite - tiempoRestante )+
                    languageManager.getText("segundos"), languageManager.getText("completacion"), JOptionPane.INFORMATION_MESSAGE);
            tiempoJugado =(long) (tiempoLimite - tiempoRestante);
            if(todosNivelesCompletados){
                JOptionPane.showMessageDialog(null, languageManager.getText("todos_completos"), ">0<", JOptionPane.INFORMATION_MESSAGE);
                  FlowFree.setScreen(new GameScreen(FlowFree,jugador));
            }
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
        this.grid = new int[PantallaJuego.manejoNivel.getNivelActual().sizeGrid]
        [PantallaJuego.manejoNivel.getNivelActual().sizeGrid];
        tiempoRestante = tiempoLimite;
        nivelCompletado = false;
        puntos.clear();
        conexiones.clear();
        celdasOcupadas.clear();
        
        for (Punto punto : puntos) {
            grid[punto.getFila()][punto.getCol()] = 1; 
            celdasOcupadas.add(new Par(punto.getCol(), punto.getFila())); 
        }
        
        detenerHiloTiempo(); 
        detenerHiloColisiones();
        musicNivel.dispose();
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

    tiempoCorriendo = true; 

    Timer.schedule(new Timer.Task() {
        @Override
        public void run() {
            if (!tiempoCorriendo || tiempoRestante <= 0 || nivelCompletado) {
                cancel(); 
                return;
            }

            tiempoRestante--; 
            labelTimer.setText(languageManager.getText("tiempo_restante") + tiempoRestante);

            if (tiempoRestante <= 0) {
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
                    } 
                }
                try{
                    Thread.sleep(50);
                } catch ( InterruptedException e){
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
    
    for (int[] posicion : pathActual) {
        int fila = posicion[1];
        int col = posicion[0];

        //skippeamos las dots finales e iniciales de la conexion
        if ((col ==dotInicialX && fila == dotInicialY) || (col ==dotFinalX && fila == dotFinalY)){
            continue;
        }
        
        if (numeroNivel!=5){
            if(grid[fila][col]==1){
                return true;
            } 
            
        }else{
            
        }
        
        if(grid[fila][col]==2){
            return true;
        }

        if(celdasOcupadas.contains(new Par(col,fila))){
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
        
        int sizeGridActual = PantallaJuego.manejoNivel.getNivelActual().sizeGrid;
        
        Vector2 posicionTocada = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);
        
        int col = (int) ((posicionTocada.x - PantallaJuego.manejoNivel.getNivelActual().offSetX) / PantallaJuego.manejoNivel.getNivelActual().sizeCelda);
        int fila = sizeGridActual - 1 - (int) ((posicionTocada.y - PantallaJuego.manejoNivel.getNivelActual().offSetY) / 
                PantallaJuego.manejoNivel.getNivelActual().sizeCelda);
               
        
        if ((col >= 0 && col < sizeGridActual) && (fila >= 0 && fila < sizeGridActual)) { //si esta en bounds
            Conexion conexionClicked = getConexionSeleccionada(col, fila);
            if (conexionClicked != null) {
            desconectarDots(conexionClicked);
            return true;
        }
            if (PantallaJuego.manejoNivel.getNivelActual().grid[fila][col] == 1) { // si la celda contiene un dot
                dotInicialX = col;
                dotInicialY = fila;
                dotFinalX = col;
                dotFinalY = fila;
                isDragging = true;
                PantallaJuego.manejoNivel.getNivelActual().pathActual = new ArrayList<>();
                PantallaJuego.manejoNivel.getNivelActual().pathActual.add(new int[]{dotInicialX, dotInicialY}); // Add the starting position
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
                        colisionDetectada = true;
                        return true;
                    }
                } else {
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
    PantallaJuego.conexionHovered = null; 
    
    if (isDragging) {
        if(colisionDetectada){
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

            if (dotInicialX == col && dotInicialY == fila) {
                isDragging = false;
                PantallaJuego.manejoNivel.getNivelActual().pathActual = null;
                return true;
            }

            if (dotFinal != null && dotFinal.getColor().equals(dotInicial.getColor())) {
                if (PantallaJuego.manejoNivel.getNivelActual().pathActual != null &&
                        !PantallaJuego.manejoNivel.getNivelActual().pathActual.isEmpty() &&
                        esPathValido(PantallaJuego.manejoNivel.getNivelActual().pathActual)) {
                    for (int[] posicion : PantallaJuego.manejoNivel.getNivelActual().pathActual) {
                        PantallaJuego.manejoNivel.getNivelActual().grid[posicion[1]][posicion[0]] = 2; 
                        celdasOcupadas.add(new Par(posicion[0],posicion[1]));
                    }
                    if (dotFinal != null && dotFinal.getColor().equals(dotInicial.getColor())) {
                    PantallaJuego.manejoNivel.getNivelActual().conexiones.add(new Conexion(dotInicial, dotFinal,
                    PantallaJuego.manejoNivel.getNivelActual().pathActual));
                    dotInicial.conectado = true;
                    dotFinal.conectado = true;
                    }
                } else {
                   
                }
            }
        }
        isDragging = false;
        PantallaJuego.manejoNivel.getNivelActual().pathActual = null; 
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
            return false;
        }
    }
    return true; 
    }
    
   public void seleccionDots(int fila, int col, Color color) {
        if (puntoSeleccionado == null) { //primer dot seleccionado
            puntoSeleccionado = new Punto(fila, col, color);
        } else {
            if(puntoSeleccionado.getColor().equals(color)){
            puntoSeleccionado = null;
            }
            else{
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
       
       if (PantallaJuego.manejoNivel.getNivelActual().conexiones.isEmpty()){
       }
       for (Conexion conexion : PantallaJuego.manejoNivel.getNivelActual().conexiones){
          for( int[] posicion : conexion.getPath()){
              if( posicion[1]== fila && posicion[0] == col){
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
           celdasOcupadas.remove(new Par(posicion[0], posicion[1])); 
           
       }
       
       PantallaJuego.manejoNivel.getNivelActual().grid[punto1.getFila()][punto1.getCol()] =1;
       PantallaJuego.manejoNivel.getNivelActual().grid[punto2.getFila()][punto2.getCol()]=1;
       

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

}
}
