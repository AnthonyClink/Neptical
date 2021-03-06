package clinkworks.neptical.datatype;

import java.util.List;
import java.util.concurrent.Callable;

import clinkworks.neptical.component.CursorProvider;
import clinkworks.neptical.domain.NSpace;

public interface Cursor {

	Cursor moveTo(String path) throws DataDefinitionException;

	Cursor moveTo(Location location);
	
	Cursor moveUp();
	Cursor moveUp(int distance);

	Cursor moveDown();
	Cursor moveDown(int distance);

	Cursor moveRight();
	Cursor moveRight(int distance);

	Cursor moveLeft();
	Cursor moveLeft(int distance);


	Location getLocation();

	Location find(String query) throws DataDefinitionException;

	NepticalData getData();

	public static interface SystemCursor extends Callable<Location>, CursorProvider, Cursor {
		NSpace currentNSpace();

		List<DataModule> activeModules();

		CursorContext activeCursorContext();
	}

}
