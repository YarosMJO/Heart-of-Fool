package TileMap;

import heartoffool.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileMap {

    // position
    private double x;
    private double y;

    // bounds
    private int xmin;
    private int ymin;
    private int xmax;
    private int ymax;

    private double tween;

    // map
    private int[][] map;
    private final int tileSize;//розмір плитки
    private int numRows;
    private int numCols;
    private int width;
    private int height;

    // tileset
    private BufferedImage tileset;
    private int numTilesAcross;//кількість плиток
    private Tile[][] tiles;

    // drawing
    private int rowOffset;//зміщення рядка
    private int colOffset;//зміщення колонки
    private final int numRowsToDraw;//номер рядка для промальвоки
    private final int numColsToDraw;//номер стовпця для промальовки

    public TileMap(int tileSize) {
        this.tileSize = tileSize;
        numRowsToDraw = GamePanel.PHEIGHT / tileSize + 10;//промальовка рядків(із запасом)7.5
        numColsToDraw = GamePanel.PWIDTH / tileSize + 10;//промальовка стовпців(із запасом)10

        tween = 0.07;
    }

    public void loadTiles(String s) {

        try {

            tileset = ImageIO.read(
                    getClass().getResourceAsStream(s)//закачуємо тропи
            );
            numTilesAcross = tileset.getWidth() / tileSize;
            tiles = new Tile[2][numTilesAcross];//на 1-й і 2-й рядки

            BufferedImage subimage;
            for (int col = 0; col < numTilesAcross; col++) {

                subimage = tileset.getSubimage(col * tileSize, 0, tileSize, tileSize);
                //записує в subimage зображення одно блоку(1 рядок -нормальні )
                tiles[0][col] = new Tile(subimage, Tile.NORMAL);

                subimage = tileset.getSubimage(col * tileSize, tileSize, tileSize, tileSize);
                //записує в subimage зображення одно блоку(2 рядок - заблоковані)
                tiles[1][col] = new Tile(subimage, Tile.BLOCKED);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadMap(String s) {

        try {

            InputStream in = getClass().getResourceAsStream(s);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(in));
            numCols = Integer.parseInt(br.readLine());//зчитуємо кількість верт. блоків
            numRows = Integer.parseInt(br.readLine());//зчитуємо кількість гориз. блоків
            map = new int[numRows][numCols];
            width = numCols * tileSize;//ширина карти
            height = numRows * tileSize;//висота карти

            xmin = GamePanel.PWIDTH - width;
            xmax = 0;
            ymin = GamePanel.PHEIGHT - height;
            ymax = 0;

            String delims = "\\s+";//розділювач

            for (int row = 0; row < numRows; row++) {
                String line = br.readLine();//зчитуємо кожний рядок карти
                String[] tokens = line.split(delims);//розбиваємо рядок на окремі елементи між пробілами

                for (int col = 0; col < numCols; col++) {
                    map[row][col] = Integer.parseInt(tokens[col]);//записуємо у масив потрібні зєднані частини
                }
                                //хххххххххххх-row[0][x](107)
                //yyyyyyyyyyyy -row[1][x](107)
                //....
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int getTileSize() {
        return tileSize;
    }

    public double getx() {
        return x;
    }

    public double gety() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getType(int row, int col) {
        int rc = map[row][col];
        int r = rc / numTilesAcross;//21/21!=0
        int c = rc % numTilesAcross;

        return tiles[r][c].getType();

    }

    public void setTile(int row, int col, int id) {
        map[row][col] = id;
    }

    public void setTween(double d) {
        tween = d;
        //землетрус
    }

    public void setPosition(double x, double y) {
        this.x += (x - this.x) * tween;
        this.y += (y - this.y) * tween;
       

        fixBounds();

        colOffset = (int) -this.x / tileSize;//зміщення колонки (кількість плиток  по горизонталі) 
        rowOffset = (int) -this.y / tileSize;//зміщення рядка(кількість плиток  по вертикалі)
    }

    private void fixBounds() {
        if (x < xmin) {
            x = xmin;
        }
        if (y < ymin) {
            y = ymin;
        }
        if (x > xmax) {
            x = xmax;
        }
        if (y > ymax) {
            y = ymax;
        }
    }

    public void draw(Graphics2D g) {

        for (int row = rowOffset;
                row < rowOffset + numRowsToDraw;
                row++) {

            if (row >= numRows) {
                break;
            }

            for (int col = colOffset;
                    col < colOffset + numColsToDraw;
                    col++) {

                if (col >= numCols) {
                    break;
                }

                if (map[row][col] == 0) {
                    continue;
                }

                int rc = map[row][col];
                int r = rc / numTilesAcross;
                int c = rc % numTilesAcross;

                g.drawImage(
                        tiles[r][c].getImage(),
                        (int) x + col * tileSize,
                        (int) y + row * tileSize,
                        null
                );

            }

        }

    }
}
