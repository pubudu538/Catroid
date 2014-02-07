package org.catrobat.catroid.uitest.content.brick.physic;

import android.widget.ListView;

import com.badlogic.gdx.math.Vector2;

import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.R;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.StartScript;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.physic.content.bricks.SetGravityBrick;
import org.catrobat.catroid.ui.ScriptActivity;
import org.catrobat.catroid.ui.adapter.BrickAdapter;
import org.catrobat.catroid.uitest.util.BaseActivityInstrumentationTestCase;
import org.catrobat.catroid.uitest.util.UiTestUtils;

import java.util.ArrayList;

public class SetGravityBrickTest extends BaseActivityInstrumentationTestCase<ScriptActivity> {

	private static final int SET_GRAVITY_X = 20;
	private static final int SET_GRAVITY_Y = 50;

	private Project project;
	private SetGravityBrick setGravityBrick;

	public SetGravityBrickTest() {
		super(ScriptActivity.class);
	}

	@Override
	public void setUp() throws Exception {
		// normally super.setUp should be called first
		// but kept the test failing due to view is null
		// when starting in ScriptActivity
		createProject();
		super.setUp();
	}

	public void testSetGravityBrick() {
		ListView dragDropListView = UiTestUtils.getScriptListView(solo);
		BrickAdapter adapter = (BrickAdapter) dragDropListView.getAdapter();

		int childrenCount = adapter.getChildCountFromLastGroup();
		int groupCount = adapter.getScriptCount();

		assertEquals("Incorrect number of bricks.", 2, dragDropListView.getChildCount());
		assertEquals("Incorrect number of bricks.", 1, childrenCount);

		ArrayList<Brick> projectBrickList = project.getSpriteList().get(0).getScript(0).getBrickList();
		assertEquals("Incorrect number of bricks.", 1, projectBrickList.size());

		assertEquals("Wrong Brick instance.", projectBrickList.get(0), adapter.getChild(groupCount - 1, 0));
		assertNotNull("TextView does not exist.", solo.getText(solo.getString(R.string.brick_set_gravity_to)));
		assertNotNull("TextView does not exist.", solo.getText(solo.getString(R.string.brick_set_gravity_unit)));

		UiTestUtils.testBrickWithFormulaEditor(solo, R.id.brick_set_gravity_edit_text_x, SET_GRAVITY_X, "gravityX",
				setGravityBrick);

		UiTestUtils.testBrickWithFormulaEditor(solo, R.id.brick_set_gravity_edit_text_y, SET_GRAVITY_Y, "gravityY",
				setGravityBrick);
	}

	private void createProject() {
		project = new Project(null, UiTestUtils.DEFAULT_TEST_PROJECT_NAME);
		Sprite sprite = new Sprite("cat");
		Script script = new StartScript(sprite);
		Vector2 initVector = new Vector2(0, 0);
		setGravityBrick = new SetGravityBrick(sprite, initVector);
		script.addBrick(setGravityBrick);

		sprite.addScript(script);
		project.addSprite(sprite);

		ProjectManager.getInstance().setProject(project);
		ProjectManager.getInstance().setCurrentSprite(sprite);
		ProjectManager.getInstance().setCurrentScript(script);

	}

}
