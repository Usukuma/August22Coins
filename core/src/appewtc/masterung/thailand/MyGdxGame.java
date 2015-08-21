package appewtc.masterung.thailand;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
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
			pigTexture, coinsTexture, rainTexture;
	private OrthographicCamera objOrthographicCamera;
	private BitmapFont nameBitmapFont, scoreBitmapFont, showScoreBitmapFont;
	private int xCloudAnInt, yCloudAnInt = 1480;
	private boolean cloudABoolean = true, finishABoolean = false;
	private Rectangle pigRectangle, coinsRectangle, rainRectangle;
	private Vector3 objVector3;
	private Sound pigSound, waterDropSound, coinsDropSound;
	private Array<Rectangle> coinsArray, rainArray;
	private long lastDropCoins, lastDropRain; //random coins ตำแหน่งใหม่ ไม่ซ้ำตำแหน่งเดิม
	private Iterator<Rectangle> coinsIterator, rainIterator; // interator เลือก java.util
	private int scoreAnInt = 0, falseAnInt = 0, finalScoreAnInt;
	private Music rainMusic, backgroundMusic;


//test test test

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

		//setup bitmapFont
		scoreBitmapFont = new BitmapFont();
		scoreBitmapFont.setColor(Color.BLUE);
		scoreBitmapFont.setScale(4);

		//setup rainTexture
		rainTexture = new Texture("droplet.png");

		//Create rainArray
		rainArray = new Array<Rectangle>();
		rainRomdomDrop();

		//setup rainMusic
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

		//set brackgrounMusic
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("bggame.mp3"));

		//setup showscore
		showScoreBitmapFont = new BitmapFont();
		showScoreBitmapFont.setColor(230, 28, 233,255);
		showScoreBitmapFont.setScale(5);


	} //create

	private void rainRomdomDrop() {
		rainRectangle = new Rectangle();
		rainRectangle.x = MathUtils.random(0, 2496);
		rainRectangle.y = 800;
		rainRectangle.width = 64;
		rainRectangle.height = 64;

		rainArray.add(rainRectangle);
		lastDropRain = TimeUtils.nanoTime();

	}

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

		//Drawable Score
		scoreBitmapFont.draw(batch, "Score =" + Integer.toString(scoreAnInt), 1000, 750);

		//Drawble Rain คำสั่ง ิ
		for (Rectangle forRain : rainArray) {
			batch.draw(rainTexture, forRain.x, forRain.y);
		}

		if (finishABoolean) {
			batch.draw(wallpaperTexture,0,0); //close screen
			showScoreBitmapFont.draw(batch, "Your Score ==>" + Integer.toString(finalScoreAnInt), 700, 950);


		}//if

		//batch.draw(img, 0, 0);
		batch.end();


		//move cloud
		moveCloud();

		//Active when touch screen
		activeTouchScreen();

		//Random Drop Coins
		randomDropCoins();

		//randomDropRain
		randomDropRain();

		//rainMusic
		rainMusic.play();

		backgroundMusic.play();

	} //render คือ ลูฟ

	private void randomDropRain() {

		if (TimeUtils.nanoTime()- lastDropRain >1E9) {
			rainRomdomDrop();
		}//if

		rainIterator = rainArray.iterator();
		while (rainIterator.hasNext()) {

			Rectangle myRainRectangle = rainIterator.next();
			myRainRectangle.y -= 50 * Gdx.graphics.getDeltaTime();

			// when rain drop into floor
			if (myRainRectangle.y + 64 < 0) {
				waterDropSound.play();
				rainIterator.remove();

			}//if

			//when Rain Overlap Pig
			if (myRainRectangle.overlaps(pigRectangle)) {
				scoreAnInt -= 1;
				waterDropSound.play();
				rainIterator.remove();
			}//if

		}//while


	}//randomDropRain

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
				falseAnInt += 1;
				waterDropSound.play();
				coinsIterator.remove(); //คืนหน่วยความจำให้ระบบ

				checkFalse();



			}//if
			//when coins overlap pig ไม่จำเป็นต้อง size เดียวกัน

			if (myCoinsRectangle.overlaps(pigRectangle)) {
				scoreAnInt += 1;
				coinsDropSound.play();
				coinsIterator.remove();
			}//if


		}//while loop

	}//randomDropCoins

	private void checkFalse() {
		if (falseAnInt > 10) {
			dispose();

			if (!finishABoolean) {
				finalScoreAnInt = scoreAnInt;

			}//if

			finishABoolean = true;



		}//if
	}//checkfalse

	@Override
	public void dispose() {
		super.dispose(); //stop game

		backgroundMusic.dispose(); // stop bg song
		rainMusic.dispose(); // stop rain sound
		pigSound.dispose();
		waterDropSound.dispose();
		coinsDropSound.dispose();


	}//dispose


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
