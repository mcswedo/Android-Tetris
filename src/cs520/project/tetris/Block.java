package cs520.project.tetris;

import javax.microedition.khronos.opengles.GL10;

class Block {
	public Block() {
		float CELL_SIZE = 0.075f;
    	int type = (int)(Math.random() * 7);
    	System.out.println(type);

    	switch(type) {
    		case 0: // I block
    			mCell = new Cell(start_x, start_y, type); // Second to bottom
    			mCell2 = new Cell(start_x, start_y + CELL_SIZE * 2, type);
    			mCell3 = new Cell(start_x, start_y + CELL_SIZE * 4, type);
    			mCell4 = new Cell(start_x, start_y - CELL_SIZE * 2, type); 
    			break;
    		case 1: // O block
    			mCell = new Cell(start_x, start_y, type); // Lower Left Corner
    			mCell2 = new Cell(start_x, start_y + CELL_SIZE * 2, type);
    			mCell3 = new Cell(start_x + CELL_SIZE * 2, start_y + CELL_SIZE * 2, type);
    			mCell4 = new Cell(start_x + CELL_SIZE * 2, start_y, type);
    			break;
    		case 2: // J Block
    			mCell = new Cell(start_x, start_y, type); // Middle of 3-line
    			mCell2 = new Cell(start_x, start_y - CELL_SIZE * 2, type);
    			mCell3 = new Cell(start_x - CELL_SIZE * 2, start_y - CELL_SIZE * 2, type);
    			mCell4 = new Cell(start_x, start_y + CELL_SIZE * 2, type);
    			break;
    		case 3: // L Block
    			mCell = new Cell(start_x, start_y, type); // Middle of 3-line
    			mCell2 = new Cell(start_x, start_y - CELL_SIZE * 2, type);
    			mCell3 = new Cell(start_x + CELL_SIZE * 2, start_y - CELL_SIZE * 2, type);
    			mCell4 = new Cell(start_x, start_y + CELL_SIZE * 2, type);
    			break;
    		case 4: // Z Block
    			mCell = new Cell(start_x, start_y, type); // Middle top
    			mCell2 = new Cell(start_x, start_y - CELL_SIZE * 2, type);
    			mCell3 = new Cell(start_x + CELL_SIZE * 2, start_y - CELL_SIZE * 2, type);
    			mCell4 = new Cell(start_x - CELL_SIZE * 2, start_y, type);
    			break;
    		case 5: // S Block
    			mCell = new Cell(start_x, start_y, type); // Middle top
    			mCell2 = new Cell(start_x, start_y - CELL_SIZE * 2, type);
    			mCell3 = new Cell(start_x - CELL_SIZE * 2, start_y - CELL_SIZE * 2, type);
    			mCell4 = new Cell(start_x + CELL_SIZE * 2, start_y, type);
    			break;
    		case 6: // T Block
    			mCell = new Cell(start_x, start_y, type); // Middle of 3-line
    			mCell2 = new Cell(start_x, start_y + CELL_SIZE * 2, type);
    			mCell3 = new Cell(start_x - CELL_SIZE * 2, start_y, type);
    			mCell4 = new Cell(start_x + CELL_SIZE * 2, start_y, type);
    			break;		
    	}
    }

    public void draw(GL10 gl) {
        mCell.draw(gl);
        mCell2.draw(gl);
        mCell3.draw(gl);
        mCell4.draw(gl);
    }
    
	public float start_x = 0.0f;
	public float start_y = 0.7f;
	
    private Cell mCell;
    private Cell mCell2;
    private Cell mCell3;
    private Cell mCell4;
}
