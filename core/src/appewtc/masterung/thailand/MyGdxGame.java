package appewtc.masterung.thailand;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;
import java.util.Vector;

public class MyGdxGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture wallpaperTexture, clouldTexture,
			pigTexture, coinsTexture;
	private OrthographicCamera objOrthographicCamera;
	private BitmapFont nameBitmapFont;
	private int xCloudAnInt, yCloudAnInt = 1480;
	private boolean cloudABoolean = true;
	private Rectangle pigRectangle, coinsRectangle;
	private Vector3 objVector3;
	private Sound pigSound, waterDropSound, coinsDropSound;
	private Array<Rectangle> coinsArray;
	private long lastDropCoins; //random coins ตำแหน่งใหม่ ไม่ซ้ำตำแหน่งเดิม
	private Iterator<Rectangle> coinsIterator; // interator เลือก java.util


	//เขียนตัวหนังสือ


	@Override
	public void create () {
		batch = new SpriteBatch();

		//กำหนดขนาดของจอที่ต้องการ

		objOrthographicCamera = new OrthographicCamera();
		objOrthographicCamera.setToOrtho(false, 2560, 1440);
		//objOrthographicCamera.setToOrtho(false, 1200, 800);

		// setup wallpaper
		wallpaperTexture = new Texture("background_ay2.gif");



		//img = new Texture("badlogic.jpg");

		//setup bitmapfont
		nameBitmapFont = new BitmapFont();
		nameBitmapFont.setColor(Color.BLUE);
		nameBitmapFont.setScale(4);

		//setup clould
		clouldTexture = new Texture("cloud.png");

		//setUp pig
		pigTexture = new Texture("pig.png");

		// setup Rectangle Pig
		pigRectangle = new Rectangle();
		pigRectangle.x = 1480;
		pigRectangle.y = 200;
		pigRectangle.width = 64;
		pigRectangle.height = 64;
		//test
		//Setup Pig Sound
		pigSound = Gdx.audio.newSound(Gdx.files.internal("pig.wav"));

		//setup coins
		coinsTexture = new Texture("coins.png");

		//create coinsArray เหรืยญแต่ละเหรียญ คือ array
		coinsArray = new Array<Rectangle>();
		coinsRandomDrop();

		//Setup waterdrop
		waterDropSound = Gdx.audio.newSound(Gdx.files.internal("water_drop.wav"));

		//setup coins drop sound
		coinsDropSound = Gdx.audio.newSound(Gdx.files.internal("coins_drop.wav"));

	} //create

	private void coinsRandomDrop() {
		coinsRectangle = new Rectangle();
		coinsRectangle.x = MathUtils.random(0, 2496);
		coinsRectangle.y = 1440;
		coinsRectangle.width = 64;
		coinsRectangle.height = 64;
		coinsArray.add(coinsRectangle);
		lastDropCoins = TimeUtils.nanoTime();


	}//coinsRandomDrop testt

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// setup screen
		objOrthographicCamera.update(); //เพื่อให้เล่นได้กับทุกเครือง
		batch.setProjectionMatrix(objOrthographicCamera.combined);

		//เอาไว้วาดภาพ object

		batch.begin();

		//drawable wallpaper
		batch.draw(wallpaperTexture, 0, 0);

		//drawable cloud
		batch.draw(clouldTexture, xCloudAnInt, yCloudAnInt);

		//drawable bitMapFont
		nameBitmapFont.draw(batch, "Coins PBRU : Phetchaburi Rajaphat U", 50, 1000);

		//Drawabel Pig
		batch.draw(pigTexture, pigRectangle.x, pigRectangle.y);

		//วาดรูปเหรียญ
		for (Rectangle forCoins: coinsArray) {
			batch.draw(coinsTexture, forCoins.x, forCoins.y);
		}


		//batch.draw(img, 0, 0);
		batch.end();


		//move cloud
		moveCloud();

		//Active when touch screen
		activeTouchScreen();

		//Random Drop Coins
		randomDropCoins();


	} //render คือ ลูฟ

	private void randomDropCoins() {
		if (TimeUtils.nanoTime() - lastDropCoins > 1E9) {
			coinsRandomDrop();

		}
		coinsIterator = coinsArray.iterator();
		while (coinsIterator.hasNext()) {

			Rectangle myCoinsRectangle = coinsIterator.next();
			myCoinsRectangle.y -= 50 * Gdx.graphics.getDeltaTime(); //coins speed


			//when coins into floor
			if (myCoinsRectangle.y+64 <0) {
				waterDropSound.play();
				coinsIterator.remove(); //คืนหน่วยความจำให้ระบบ


			}//if
			//when coins overlap pig ไม่จำเป็นต้อง size เดียวกัน

			if (myCoinsRectangle.overlaps(pigRectangle)) {
				coinsDropSound.play();
				coinsIterator.remove();
			}//if


		}//while loop

	}//randomDropCoins
	// หนึ่งยกกำลังเก้า 1E9


	private void activeTouchScreen() {

		if (Gdx.input.isTouched()) {

			//Sound Effect Pig
			pigSound.play();

			objVector3 = new Vector3();
			objVector3.set(Gdx.input.getX(), Gdx.input.getY(), 0);

			if (objVector3.x < Gdx.graphics.getWidth()/2){

				if (pigRectangle.x < 0) {
					pigRectangle.x = 0;
				} else {
					pigRectangle.x -= 10;
				}


			} else {
				if (pigRectangle.x > 2500) {
					pigRectangle.x = 2500;
				} else {
					pigRectangle.x += 10;
				}
				//pigRectangle.x += 10;
			}

		}//if


	}//activeTouchScreen

	private void moveCloud() {

		if (cloudABoolean) {
			if (xCloudAnInt < 2297) {
				xCloudAnInt += 300 * Gdx.graphics.getDeltaTime();
			} else {
				cloudABoolean = !cloudABoolean;
			}

		} else {
			if (xCloudAnInt > 0) {
				xCloudAnInt -= 300 * Gdx.graphics.getDeltaTime();
			} else {
				cloudABoolean = !cloudABoolean;
			}
		}

	}//movecloud


} // finish for the day, start again tomorrow