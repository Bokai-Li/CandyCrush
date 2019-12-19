//final project
import java.applet.Applet;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.util.stream.*;

public class CandyCrushApplet extends Applet implements ItemListener
{
	private	Checkbox c2, c3, c4, c5, c6;
  	private CheckboxGroup numColor;
  	//private Label colorL, rowL, colL;
  	private Choice rowc, colc;

	private Image virtualMem;
	private Graphics gBuffer;
	private Image[] candyImages;
	private Image[] otherImages;

	private Candy[] candys;
	private Candy[][] board;

	private Rectangle[] candyBlocks;
	private Rectangle playButton, restartButton;
	private int appletWidth, appletHeight;
	private int candyType;
	private int score;
	private int moveLeft;
	private int maxRow=5;
	private int  maxCol=5;
	private double dropSpeed = 0;//was 1
	private int fillSpeed = 0;//was 5
	private int selectedIndex = -1;
	private int selectedIndex2 = -1;
	private int bombPositionIndex=-1;
	private int rowForDrop;

	private int moveIndex = -1;

	private boolean playerMoved = false;
	private boolean firstStart = true;
	private boolean isLegal = false;
	private boolean processingChocoSpecial = false;
	private boolean menuScreen = true;
	private boolean gameOver =false;
	private boolean gameOverCheckFinished = false;


	private int numOfColor = 4;

	public void init()
	{
		appletWidth = getWidth();
		appletHeight = getHeight();
		virtualMem = createImage(appletWidth,appletHeight);
		gBuffer = virtualMem.getGraphics();
		score = 0;
		moveLeft=20;
		playButton = new Rectangle(342,410,340,123);
		if(menuScreen)
		{

			numColor = new CheckboxGroup();
			//colorL.setText();
 			c2 = new Checkbox("2", numColor, false);
			c3 = new Checkbox("3", numColor, false);
			c4 = new Checkbox("4", numColor, true);
			c5 = new Checkbox("5", numColor, false);
			c6 = new Checkbox("6", numColor, false);

			c2.addItemListener(this);
			c3.addItemListener(this);
			c4.addItemListener(this);
			c5.addItemListener(this);
			c6.addItemListener(this);

			add(c2);
			add(c3);
			add(c4);
			add(c5);
			add(c6);


			rowc = new Choice();
			rowc.add("4");
			rowc.add("5");
			rowc.add("6");
			rowc.add("7");
			rowc.add("8");
			rowc.add("9");
			rowc.add("10");

			add(rowc);
			colc = new Choice();
			colc.add("4");
			colc.add("5");
			colc.add("6");
			colc.add("7");
			colc.add("8");
			colc.add("9");
			colc.add("10");
			add(colc);

			gBuffer.drawString("Number of colors",391,40);
			gBuffer.drawString("row",547,40);
			gBuffer.drawString("column",593,40);
		}

	}


	public void initializeGame()
	{
		String str = "";
		if(c2.getState() == true)
			numOfColor=2;
		else
	    	if(c3.getState() == true)
	    		numOfColor=3;
			else
				if(c4.getState() == true)
					numOfColor=4;
					else
						if(c5.getState() == true)
							numOfColor=5;
						else
							if(c6.getState() == true)
								numOfColor = 6;

			switch(rowc.getSelectedItem())
			{
				case "4":
					maxRow = 4;
					break;
				case "5":
					maxRow = 5;
					break;
				case "6":
					maxRow = 6;
					break;
				case "7":
					maxRow = 7;
					break;
				case "8":
					maxRow = 8;
					break;
				case "9":
					maxRow = 9;
					break;
				case "10":
					maxRow = 10;
					break;
			}
			switch(colc.getSelectedItem())
			{
				case "4":
					maxCol = 4;
					break;
				case "5":
					maxCol = 5;
					break;
				case "6":
					maxCol = 6;
					break;
				case "7":
					maxCol = 7;
					break;
				case "8":
					maxCol = 8;
					break;
				case "9":
					maxCol = 9;
					break;
				case "10":
					maxCol = 10;
					break;
			}


			int x,y;


			//maxRow = 15;
			//maxCol = 10;
		//	numOfColor = 4;

			candyBlocks = new Rectangle[maxRow*maxCol];
			candyImages = new Image[40];
			otherImages = new Image[3];

			candys = new Candy[maxRow*maxCol];
			board = new Candy[maxRow][maxCol];
			//candyImages[0]=getImage(getDocumentBase(), "orange.jpg");
			//candyImages[1]=getImage(getDocumentBase(), "blue.jpg");
			//candyImages[2]=getImage(getDocumentBase(), "green.jpg");
			//candyImages[3]=getImage(getDocumentBase(), "purple.jpg");
			//candyImages[4]=getImage(getDocumentBase(), "red.jpg");
			//candyImages[5]=getImage(getDocumentBase(), "yellow.jpg");
			//candyImages[6]=getImage(getDocumentBase(), "score.jpg");
			//candyImages[7]=getImage(getDocumentBase(), "white.jpg");
			candyImages[0]=getImage(getDocumentBase(), "orange.png");
			candyImages[1]=getImage(getDocumentBase(), "blue.png");
			candyImages[2]=getImage(getDocumentBase(), "green.png");
			candyImages[3]=getImage(getDocumentBase(), "purple.png");
			candyImages[4]=getImage(getDocumentBase(), "red.png");
			candyImages[5]=getImage(getDocumentBase(), "yellow.png");
			candyImages[6]=getImage(getDocumentBase(), "score.jpg");
			candyImages[7]=getImage(getDocumentBase(), "white.jpg");

			otherImages[0]=getImage(getDocumentBase(), "ScoreBoard.jpg");
			otherImages[1]=getImage(getDocumentBase(), "moveBoard.jpg");

			candyImages[10]=getImage(getDocumentBase(), "chocolate.png");
			candyImages[11]=getImage(getDocumentBase(), "wraporange.png");
			candyImages[12]=getImage(getDocumentBase(), "wrapblue.png");
			candyImages[13]=getImage(getDocumentBase(), "wrapgreen.png");
			candyImages[14]=getImage(getDocumentBase(), "wrappurple.png");
			candyImages[15]=getImage(getDocumentBase(), "wrapred.png");
			candyImages[16]=getImage(getDocumentBase(), "wrapyellow.png");

			candyImages[20]=getImage(getDocumentBase(), "vorange.png");
			candyImages[21]=getImage(getDocumentBase(), "vblue.png");
			candyImages[22]=getImage(getDocumentBase(), "vgreen.png");
			candyImages[23]=getImage(getDocumentBase(), "vpurple.png");
			candyImages[24]=getImage(getDocumentBase(), "vred.png");
			candyImages[25]=getImage(getDocumentBase(), "vyellow.png");

			candyImages[30]=getImage(getDocumentBase(), "horange.png");
			candyImages[31]=getImage(getDocumentBase(), "hblue.png");
			candyImages[32]=getImage(getDocumentBase(), "hgreen.png");
			candyImages[33]=getImage(getDocumentBase(), "hpurple.png");
			candyImages[34]=getImage(getDocumentBase(), "hred.png");
			candyImages[35]=getImage(getDocumentBase(), "hyellow.png");

			for(int i=0; i<maxRow*maxCol; i++)
			{
				x=(i%maxCol)*50+50;
				y=(i/maxCol)*50+50;
				candyBlocks[i] = new Rectangle(x, y, 50, 50);
				//System.out.println(x+" "+y);
			}


			Candy c = new Candy(0,0,0,candyImages[0],0);

			int i = 0;
			for(int row =0; row<maxRow; row++)
			{
				for(int col=0; col<maxCol; col++)
				{

					candyType = (int)(Math.random() * numOfColor);

					c=new Candy(col+row*maxCol, col, row, candyImages[candyType], candyType);

					candys[i] = c;
					board[row][col]=c;

					i++;
				}
			}

	}
	public void itemStateChanged(ItemEvent e)
	{

	 }
	public boolean mouseDown(Event e, int x, int y)
	{
		if(!menuScreen && !gameOver)
		{
			playerMoved = true;
			//select
			if(selectedIndex2==-1 && selectedIndex==-1)
			{
				for(int j=0; j<candyBlocks.length;j++)
				{
					if(candyBlocks[j].inside(x,y))
					{
						selectedIndex=j;
						//System.out.println(j+" "+candyBlocks[j].getX()+" "+candyBlocks[j].getY());
					}
				}
			}

			if(selectedIndex>=0)
			{
				for(int h=0; h<candyBlocks.length;h++)
				{
					if(h!=selectedIndex && candyBlocks[h].inside(x,y))
					{
						selectedIndex2=h;
					}
				}
			}
			//repaint();
			//System.out.println(selectedIndex+" "+selectedIndex2);
			return true;
		}
		else
		{

			//gBuffer.drawString("x: "+x+"y: "+y,0,800);
			//System.out.println("x: "+x+"y: "+y);
			if(playButton.inside(x,y))
			{
				menuScreen = false;
				initializeGame();
				gBuffer.setColor(Color.white);
				gBuffer.fillRect(0,0,appletWidth, appletHeight);
				c2.setVisible(false);
				c3.setVisible(false);
				c4.setVisible(false);
				c5.setVisible(false);
				c6.setVisible(false);
				rowc.setVisible(false);
				colc.setVisible(false);
			}
			return true;
		}

	}

	public void paintCandy(Graphics g)
	{
		int x,y;

		for(int row =0; row<maxRow; row++)
		{
			for(int col=0; col<maxCol; col++)
			{
				x=col*50+50;
				y=row*50+50;
				gBuffer.drawImage(board[row][col].getImage(), x, y, this);
			}
		}
		//gBuffer.drawImage(otherImages[0],maxCol*50+100, 4*50+50, this);

		g.drawImage(virtualMem,0,0,this);
	}

	public void playerMove(Graphics g)
	{
		int temp;
		int clearType;

		gBuffer.setColor(Color.red);
		if(selectedIndex>=0 && selectedIndex2==-1)
		{
			gBuffer.drawRect((int)candyBlocks[selectedIndex].getX(),(int)candyBlocks[selectedIndex].getY(),49,49);
			playerMoved = true;
		}
		//System.out.println("hit selection box " + playerMoved);
		g.drawImage(virtualMem,0,0,this);
		if(selectedIndex2!=-1)
		{
			temp = score;
			moveCandy(g,selectedIndex,selectedIndex2,swapCase(selectedIndex,selectedIndex2),12);
			moveIndex = selectedIndex2;
			//clearMatch(g);

			if (	//check if next to each other
					(candys[selectedIndex].getRow()==candys[selectedIndex2].getRow() &&
					Math.abs(candys[selectedIndex].getCol()-candys[selectedIndex2].getCol()) == 1 )
					||
					(candys[selectedIndex].getCol()==candys[selectedIndex2].getCol() &&
					Math.abs(candys[selectedIndex].getRow()-candys[selectedIndex2].getRow()) == 1)
				)
			{
				for(int row =0; row<maxRow; row++)
				{
					for(int col=0; col<maxCol; col++)
					{
						if(board[row][col].getSpecialCase() == 0)
						{
							board[row][col].setSpecialCase(1);
						}
					}
				}

				if(candys[selectedIndex].getType() == 10 && candys[selectedIndex2].getSpecialCase() == 1)
				{														//bomb and choco

					moveLeft--;
					clearType = candys[selectedIndex2].getType();
					board[candys[selectedIndex].getRow()][candys[selectedIndex].getCol()].clearableTrue();
					chocolateBombClear(clearType);

					processingChocoSpecial = true;
				}
				else
					if(candys[selectedIndex2].getType() == 10 && candys[selectedIndex].getSpecialCase() == 1)
					{														//choco and bomb

						moveLeft--;
						clearType = candys[selectedIndex].getType();
						board[candys[selectedIndex2].getRow()][candys[selectedIndex2].getCol()].clearableTrue();
						chocolateBombClear(clearType);

						processingChocoSpecial = true;
					}
					else
						if(candys[selectedIndex].getType() == 10 && (candys[selectedIndex2].getSpecialCase() == 5 ||candys[selectedIndex2].getSpecialCase() == 6))
						{												//choco and striped
							moveLeft--;
							clearType = candys[selectedIndex2].getType();
							board[candys[selectedIndex].getRow()][candys[selectedIndex].getCol()].clearableTrue();
							chocolateStripedClear(clearType);

							processingChocoSpecial = true;
						}
						else
							if(candys[selectedIndex2].getType() == 10 && (candys[selectedIndex].getSpecialCase() == 5 ||candys[selectedIndex].getSpecialCase() == 6))
							{											//choco and striped
								moveLeft--;
								clearType = candys[selectedIndex].getType();
								board[candys[selectedIndex].getRow()][candys[selectedIndex].getCol()].clearableTrue();
								chocolateStripedClear(clearType);

								processingChocoSpecial = true;
							}
							else
								if(candys[selectedIndex].getSpecialCase() == 1 && candys[selectedIndex2].getSpecialCase() == 1)
								{														//both are bombs
									moveLeft--;
									doubleBombClear();
								}
								else
									if((candys[selectedIndex].getSpecialCase() == 5 || candys[selectedIndex].getSpecialCase() == 6) &&
										(candys[selectedIndex2].getSpecialCase() == 5 || candys[selectedIndex2].getSpecialCase() == 6))
									{														//both are striped
										moveLeft--;
										candys[selectedIndex].setNormal();
										board[candys[selectedIndex].getRow()][candys[selectedIndex].getCol()].setNormal();
										int c=candys[selectedIndex2].getCol();
										int r=candys[selectedIndex2].getRow();
										doubleStripedClear(r,c);
									}

									else
										if(candys[selectedIndex2].getSpecialCase() == 1 && (candys[selectedIndex].getSpecialCase() == 5 ||candys[selectedIndex].getSpecialCase() == 6))
										{											//bomb and striped
											moveLeft--;
											bombStripedClear();
											candys[selectedIndex2].setSpecialCase(2);

										}
										else
											if(candys[selectedIndex].getSpecialCase() == 1 && (candys[selectedIndex2].getSpecialCase() == 5 ||candys[selectedIndex2].getSpecialCase() == 6))
											{											//bomb and striped
												moveLeft--;
												bombStripedClear();
												candys[selectedIndex].setSpecialCase(2);
											}
											else

												if(candys[selectedIndex].getType() == 10 && candys[selectedIndex2].getType() == 10)
												{
													moveLeft --;										//both are chocolate ball
													for(int row =0; row<maxRow; row++)
													{
														for(int col=0; col<maxCol; col++)
														{
															board[row][col].clearableTrue();
															//gBuffer.drawLine(100, 100, 500, 500);
														}
													}
												}
												else
													if(candys[selectedIndex].getType() == 10)//first one is chocolate ball
													{
														clearType = candys[selectedIndex2].getType();
														moveLeft --;
														candys[selectedIndex].clearableTrue();
														board[candys[selectedIndex].getRow()][candys[selectedIndex].getCol()].clearableTrue();
														chocolateClear(clearType);


													}
													else
														if(candys[selectedIndex2].getType() == 10)//second one is chocolate ball
														{
															moveLeft --;
															clearType = candys[selectedIndex].getType();
															candys[selectedIndex2].clearableTrue();
															board[candys[selectedIndex2].getRow()][candys[selectedIndex2].getCol()].clearableTrue();
															chocolateClear(clearType);
														}
														else										//normal move
															if(isLegal())
															{
																moveLeft --;
															}
															else									//not a legal move
															{
																moveCandy(g,selectedIndex,selectedIndex2,swapCase(selectedIndex,selectedIndex2),12);

															}
					}
			//System.out.println(selectedIndex+" "+selectedIndex2);
			selectedIndex = -1;
			selectedIndex2 = -1;
		}

	}

	public void chocolateClear(int type)
	{
		for(int row =0; row<maxRow; row++)
		{
			for(int col=0; col<maxCol; col++)
			{
				if(board[row][col].getType() == type)
				{
					board[row][col].clearableTrue();
				}
			}
		}
	}

	public void chocolateStripedClear(int type)
	{
		for(int row =0; row<maxRow; row++)
		{
			for(int col=0; col<maxCol; col++)
			{
				if(board[row][col].getType() == type)
				{
					int rand =(int)(Math.random() * 2);
					if( rand == 1)
						board[row][col].setHStriped(candyImages[type+30]);
					else
						board[row][col].setVStriped(candyImages[type+20]);

					board[row][col].shouldBomb();
					//board[row][col].clearableFalse();
				}
			}
		}

	}

	public void chocolateBombClear(int type)
	{
		for(int row =0; row<maxRow; row++)
		{
			for(int col=0; col<maxCol; col++)
			{
				if(board[row][col].getType() == type)
				{
					board[row][col].setBomb(candyImages[type+11]);
					board[row][col].shouldBomb();
					//board[row][col].clearableTrue();
				}
			}
		}
	}

	public void markSingle(int row, int col)
	{
		if(row>=0 && row<maxRow && col>=0 && col<maxCol)
		{
			board[row][col].clearableTrue();
			if(board[row][col].getType() == 10)
			{
				chocolateClear((int)(Math.random() * numOfColor));
			}
		}
	}

	public int getTypeIfinBoard(int row, int col)
	{
		if(row>=0 && row<maxRow && col>=0 && col<maxCol)
		{
			return board[row][col].getType();
		}
		return -2;
	}

	public void doubleBombClear()
	{
		int colcenter=candys[selectedIndex2].getCol();
		int rowcenter=candys[selectedIndex2].getRow();

		for(int row = rowcenter-2; row <= rowcenter+2; row++)
			for(int col = colcenter-2; col <= colcenter+2; col++)
			{
				markSingle(row,col);
			}

		board[rowcenter][colcenter].clearableFalse();
		board[rowcenter][colcenter].setSpecialCase(3);
	}
	public void doubleStripedClear( int rowcenter, int colcenter)
	{

		for(int row = 0; row <= maxRow; row++)
		{
			markSingle(row,colcenter);
		}
		for(int col = 0; col <= maxCol; col++)
		{
			markSingle(rowcenter,col);
		}

	}
	public void bombStripedClear()
	{
		int colcenter=candys[selectedIndex2].getCol();
		int rowcenter=candys[selectedIndex2].getRow();
		for(int row = 0; row <= maxRow; row++)
		{
			markSingle(row,colcenter);
			markSingle(row,colcenter+1);
			markSingle(row,colcenter-1);
		}
		for(int col = 0; col <= maxRow; col++)
		{
			markSingle(rowcenter,col);
			markSingle(rowcenter+1,col);
			markSingle(rowcenter-1,col);
		}
	}

	public void markHStripPosition()
	{
		int count = 0;
		for(int type = 0; type < numOfColor; type++)
		{
			int[] rowCount = countRowColor(type);
			for(int row=0; row < rowCount.length; row++)
			{
				if (rowCount[row] >= 4)
				{
					int col = 0;
					while(col < maxCol-3)
					{
						if(board[row][col].getType()==type)
						{
							col++;
							if(board[row][col].getType()==type)
							{
								col++;
								if(board[row][col].getType()==type)
								{
									col++;
									if(board[row][col].getType()==type)
									{
										if(board[row][col-1].getSpecialCase() == -1)
										{
											board[row][col].setInvalidType();
											board[row][col].clearableTrue();
											board[row][col-2].setInvalidType();
											board[row][col-2].clearableTrue();
											board[row][col-3].setInvalidType();
											board[row][col-3].clearableTrue();
											board[row][col-1].setVStriped(candyImages[type+20]);
											candys[board[row][col-1].getIndex()].setVStriped(candyImages[type+20]);
										}
										else
										{
											board[row][col].clearableTrue();
										}
										//else
											//if(board[row][candys[selectedIndex].getCol()].getSpecialCase()== 5||
											//	board[row][candys[selectedIndex].getCol()].getSpecialCase()== 6)
											//if(candys[selectedIndex].getType()=type)
									//		{
									//			board[row][candys[selectedIndex].getCol()].setHStriped(candyImages[type+30]);
												//candys[selectedIndex].setHStriped(candyImages[type+30]);
									//		}
										//	else
										//		if(candys[selectedIndex2].getType()=type)
										//		{
										//			board[candys[selectedIndex2].getRow()][candys[selectedIndex].getCol()].setHStriped(candyImages[type+30]);
										//			candys[selectedIndex2].setHStriped(candyImages[type+30]);
										//		}
										//col++;
										//do
										//{
										//	count++;
										//	col--;
										//}while(count < 3 && (board[row][col].getSpecialCase() == 5 || board[row][col].getSpecialCase() == 6));
										//	board[row][col].setHStriped(candyImages[type+30]);
										//	candys[board[row][col].getIndex()].setHStriped(candyImages[type+30]);
									}


								}
							}
						}
					col++;
					}
				}
			}
		}

	}

	public void markVStripPosition()
	{
		int count=0;
		for(int type = 0; type < numOfColor; type++)
		{
			int[] colCount = countColColor(type);
			for(int col=0; col < colCount.length; col++)
			{
				if (colCount[col] >= 4)
				{
					int row = 0;
					while(row < maxRow-3)
					{
						if(board[row][col].getType()==type)
						{
							row++;
							if(board[row][col].getType()==type)
							{
								row++;
								if(board[row][col].getType()==type)
								{
									row++;
									if(board[row][col].getType()==type)
									{
									//	if(selectedIndex == -1 && selectedIndex2 == -1)
									//	{
											board[row-1][col].setInvalidType();
											board[row-1][col].clearableTrue();
											board[row-2][col].setInvalidType();
											board[row-2][col].clearableTrue();
											board[row-3][col].setInvalidType();
											board[row-3][col].clearableTrue();
											if(board[row][col].getSpecialCase() == -1)
											{
												board[row][col].setHStriped(candyImages[type+30]);
												candys[board[row][col].getIndex()].setHStriped(candyImages[type+30]);
											}
											else
											{
												board[row][col].clearableTrue();
											}
									//	}
										//else
										//{
									//		board[candys[selectedIndex].getRow()][col].setVStriped(candyImages[type+30]);
									//	}
									//	row++;
									//	do
									//	{
									//		count++;
									//		row--;
									//	}while(count < 3 && (board[row][col].getSpecialCase() == 5 || board[row][col].getSpecialCase() == 6));
									//		board[row][col].setHStriped(candyImages[type+30]);
									//		candys[board[row][col].getIndex()].setHStriped(candyImages[type+30]);
									}
								}
							}
						}
					row++;
					}
				}
			}
		}

	}

	public void markBombPosition()
	{

		for(int type = 0; type < numOfColor; type++)
		{
			int[] rowCount = countRowColor(type);
			for(int row=0; row < rowCount.length; row++)
			{

				if (rowCount[row] >= 3)
				{
					int col = 0;
					while(col < maxCol-2)
					{
						if(board[row][col].getType()==type)
						{
							col++;
							if(board[row][col].getType()==type)
							{
								col++;
								if(board[row][col].getType()==type)
								{
									if(bombHelper(row,col))
									{
										board[row][col].clearableFalse();
										board[row][col-1].setInvalidType();
										board[row][col-1].clearableTrue();
										board[row][col-2].setInvalidType();
										board[row][col-2].clearableTrue();
										bombPositionIndex = board[row][col].getIndex();
									}
									else
										if(bombHelper(row,col-1))
										{
											board[row][col].setInvalidType();
											board[row][col].clearableTrue();
											board[row][col-2].setInvalidType();
											board[row][col-2].clearableTrue();
											board[row][col-1].clearableFalse();
											bombPositionIndex = board[row][col].getIndex();
										}
										else
											if(bombHelper(row,col-2))
											{
												board[row][col].setInvalidType();
												board[row][col].clearableTrue();
												board[row][col-1].setInvalidType();
												board[row][col-1].clearableTrue();
												board[row][col-2].clearableFalse();
												bombPositionIndex = board[row][col].getIndex();
											}
								}
							}
						}
					col++;
					}
				}
			}
		}

	}

	public boolean bombHelper(int row, int col)
	{
		int type = board[row][col].getType();
		//*			  *			*	***
		//*			  *			*	***
		//* * *		* * *	* * *	***
		if(row>=2)
		{
			if(board[row-1][col].getType()==type && board[row-2][col].getType()==type &&
					!board[row-1][col].getProcessBomb() && !board[row-2][col].getProcessBomb())
			{
				//if(board[row][col].getSpecialCase() != 1)
				//{
					//board[row][col].setInvalidType();
					board[row-1][col].setInvalidType();
					board[row-1][col].clearableTrue();
					board[row-2][col].setInvalidType();
					board[row-2][col].clearableTrue();
					board[row][col].setBomb(candyImages[type+11]);
					candys[board[row][col].getIndex()].setBomb(candyImages[type+11]);

				//}
				//else
				//	board[row][col].clearableTrue();
				return true;
			}
		}
		//*			   *			 *
		//*	* *		 * * *		 * * *
		//*			   *			 *
		if(row>=1 && row < maxRow-1)
		{
			if(board[row-1][col].getType()==type && board[row+1][col].getType()==type
					&&
					!board[row-1][col].getProcessBomb() && !board[row+1][col].getProcessBomb())
			{
				//if(board[row][col].getSpecialCase() != 1)
				//{
					//board[row][col].setInvalidType();
					board[row-1][col].setInvalidType();
					board[row-1][col].clearableTrue();
					board[row+1][col].setInvalidType();
					board[row+1][col].clearableTrue();
					board[row][col].setBomb(candyImages[type+11]);
					candys[board[row][col].getIndex()].setBomb(candyImages[type+11]);

				//}
				//else
				//	board[row][col].clearableTrue();
					return true;
			}
		}

		//*	* *		* *	*	* *	*
		//*			  *			*
		//* 		  * 	 	*
		if(row	< maxRow-2)
		{
			if(board[row+1][col].getType()==type && board[row+2][col].getType()==type
					&&
					!board[row+1][col].getProcessBomb() && !board[row+2][col].getProcessBomb())
			{
				//if(board[row][col].getSpecialCase() != 1)
				//{
					//board[row][col].setInvalidType();
					board[row+1][col].setInvalidType();
					board[row+1][col].clearableTrue();
					board[row+2][col].setInvalidType();
					board[row+2][col].clearableTrue();
					board[row][col].setBomb(candyImages[type+11]);
					candys[board[row][col].getIndex()].setBomb(candyImages[type+11]);

					//}
					//else
					//	board[row][col].clearableTrue();
					return true;
			}
		}

		resetProcessBomb();
		return false;

	}

	public boolean isLegal()
	{
		boolean legal = false;
		markAllMatch();
		for(int row =0; row<maxRow; row++)
		{
			for(int col=0; col<maxCol; col++)
			{
				if(board[row][col].getClearable())
				{
					legal = true;
				}
			}
		}
		return legal;
	}
	public void resetProcessBomb()
	{
		for(int row =0; row<maxRow; row++)
		{
			for(int col=0; col<maxCol; col++)
			{
				board[row][col].resetProcessB();
			}
		}
	}

	public void markAllMatch()
	{
		//System.out.println("markAll enter");
		for(int type = 0; type < numOfColor; type++)
		{
			markMatchHorizontal(type);
			markMatchVertical(type);
		}

		markChocolatePosition();
		markBombPosition();
		markVStripPosition();
		markHStripPosition();

	//	System.out.println("markAll exit");

	}
	public void fillAndDrop(Graphics g)
	{
		boolean inProgress = true;

		while(inProgress)
		{
			fillRandom(g);
			drop(g);
			inProgress = false;
			for(int row =0; row<maxRow; row++)
			{
				for(int col=0; col<maxCol; col++)
				{
					if(board[row][col].getClearable())
					{
						inProgress = true;
					}
				}
			}
		}
		repaint();
	}
	public void paint(Graphics g)
	{
		int i = 0;
		Candy c;

		if(!menuScreen && !gameOver)
		{
			if(needShuffle())
			{
				for(int row =0; row<maxRow; row++)
				{
					for(int col=0; col<maxCol; col++)
					{
						if(board[row][col].getType() != 10 && board[row][col].getSpecialCase() == -1)
						{

							candyType = (int)(Math.random() * numOfColor);

							c=new Candy(col+row*maxCol, col, row, candyImages[candyType], candyType);

							candys[board[row][col].getIndex()] = c;
							board[row][col]=c;

						}
					}
				}
			}
			if(playerMoved)
			{
				//if(!processingChocoSpecial)
				//{

					//processing = true
					playerMove(g);
					paintCandy(g);
					markAllMatch();
					//System.out.println("markAll");
					clearMatch(g);

					fillAndDrop(g);
					for(int row =0; row<maxRow; row++)
					{
						for(int col=0; col<maxCol; col++)
						{
							if(board[row][col].getSpecialCase() == 0 && board[row][col].getIndex() != bombPositionIndex )
							{
								board[row][col].setSpecialCase(1);
							}
						}
					}

					//processing = false


				//playerMove(g);
				//}
				//else
				//{
					//while(processingChocoSpecial)
					//{



			/*			for(int row =0; row<maxRow; row++)
						{
							for(int col=0; col<maxCol; col++)
							{
								if(board[row][col].getShouldBomb() && (board[row][col].getSpecialCase() == 1))
								{
									for(int r= row-1; r <= row+1; r++)
										for(int c = col-1; c <= col+1; c++)
										{
												markSingle(r,c);
										}
								}
								else
									if(board[row][col].getShouldBomb() && (board[row][col].getSpecialCase() == 5))
									{ 	//vertical
										for(row = 0; row <= maxRow; row++)
										{
											markSingle(row,col);
										}

									}
									else
										if(board[row][col].getShouldBomb() && (board[row][col].getSpecialCase() == 6))
										{ 	//vertical
											for(col = 0; col <= maxCol; col++)
											{
												markSingle(row,col);
											}
										}

							}
						}
					*/


					//}
					//processingChocoSpecial = false;
				//}

			}
			else
				if(!firstStart)
				{
					delay(500);
					paintCandy(g);
					fillAndDrop(g);
					markAllMatch();
					clearMatch(g);
				}
				else
				{
					paintCandy(g);
					firstStart = false;
				}
		}
		else
			if(!gameOver)
			{
				drawScreen();
			}


		if(moveLeft <= 0 && !gameOverCheckFinished)
		{
			dropSpeed = 0;
			fillSpeed = 0;
			//numOfColor = 2;
			gameOver = true;
			gameOverCheckFinished =false;
			for(int row = 0; row<maxRow; row++)
			{
				for(int col=0; col<maxCol; col++)
				{
					if(board[row][col].getSpecialCase() != -1 || board[row][col].getType()==10)
					{
						markSingle(row, col);
					}
				}
			}
			while(anyClearable())
			{
				//for(int row = 0; row<maxRow; row++)
				//{
				//	for(int col=0; col<maxCol; col++)
				//	{
				//		if(board[row][col].getSpecialCase() != -1 || board[row][col].getType()==10)
				//		{
				//			board[row][col].clearableTrue();
				//		}
				//	}
				//}
				clearMatch(g);
				fillAndDrop(g);
			}
			//anyClearable();

		}
		if(gameOver && gameOverCheckFinished)
		{
			gBuffer.drawImage(getImage(getDocumentBase(), "wonderful.jpg"),0,100,this);
			gBuffer.drawImage(otherImages[0],100, 400, this);
			gBuffer.setFont(new Font("Brush Script MT", Font.BOLD, 36));
			gBuffer.drawString(""+score, 140,520);
		}
		g.drawImage(virtualMem,0,0,this);
	}

	public boolean anyClearable()
	{
		markAllMatch();
		for(int row = 0; row<maxRow; row++)
		{
			for(int col=0; col<maxCol; col++)
			{
				if(board[row][col].getClearable() || board[row][col].getSpecialCase() == 2)
				{
					return true;
				}
			}
		}
		for(int row = 0; row<maxRow; row++)
		{
			for(int col=0; col<maxCol; col++)
			{
				if(!(board[row][col].getSpecialCase() == -1) || (board[row][col].getType() == 10))
				{
					return false;
				}
			}
		}
		gameOverCheckFinished = true;
		return false;
	}

	public void drawScreen()
	{
		gBuffer.drawImage(getImage(getDocumentBase(), "Menu.png"),0,50,this);
	}
	public void update(Graphics g)
	{
		paint(g);
	}

	public void waitRedraw(Graphics g)
	{
		Candy c;
		c = board[0][0];
		for(int y=0; y <=50; y += 5)
		{
			drawOtherCandys2();
			gBuffer.drawImage(c.getImage(),50,50,this);
			g.drawImage (virtualMem,0,0,this);
			delay(15);
		}
	}
	public void moveCandy(Graphics g, Image image, int x)
	{
		for(int y=0; y <=50; y += 5)
		{
			drawOtherCandys();
			gBuffer.drawImage(image,x,y,this);
			g.drawImage (virtualMem,0,0,this);
			delay(fillSpeed);
		}
		//delay(10);
	}

	public void moveCandy(Graphics g, int CI1, int CI2, int sCase,double delay)
	{
		int x,x2,y,y2,row1,row2,col1,col2;
		Candy c1, c2, temp;
		Image cIm1, cIm2;
		temp = new Candy();
		if(sCase != -1)
		{

			c1 = candys[CI1];
			c2 = candys[CI2];
			cIm1 = c1.getImage();
			cIm2 = c2.getImage();
			row1 = c1.getRow();
			row2 = c2.getRow();
			col1 = c1.getCol();
			col2 = c2.getCol();
			x=col1*50+50;
			x2=col2*50+50;
			y=row1*50+50;
			y2=row2*50+50;

			switch(sCase)
			{
				case 1://left
					for(x = col1*50+50, x2 = col2*50+50 ; x >= col2*50+50; x -= 5, x2+= 5)
					{
						drawOtherCandys();
						gBuffer.drawImage(cIm2,x2,y,this);
						gBuffer.drawImage(cIm1,x,y,this);
						g.drawImage (virtualMem,0,0,this);
						delay(delay);
					}
					break;
				case 2://right
					for(x = col1*50+50, x2 = col2*50+50 ; x <= col2*50+50; x2 -= 5, x+= 5)
					{
						drawOtherCandys();
						gBuffer.drawImage(cIm2,x2,y,this);
						gBuffer.drawImage(cIm1,x,y,this);
						g.drawImage (virtualMem,0,0,this);
						delay(delay);
					}
					break;
				case 3://up
					for(y=row1*50+50, y2=row2*50+50 ; y >= row2*50+50; y -= 5, y2+= 5)
					{
						drawOtherCandys();
						gBuffer.drawImage(cIm2,x,y2,this);
						gBuffer.drawImage(cIm1,x,y,this);
						g.drawImage (virtualMem,0,0,this);
						delay(delay);
					}
					break;
				//case 5:
				//	for(y=row1*50+50; y <= row2*50+50; y += 5)
				//	{
				//		drawOtherCandys();
				//		gBuffer.drawImage(cIm1,x,y,this);
				//		g.drawImage (virtualMem,0,0,this);
				//		delay(delay);
				//	}
				//	break;
				case 4://down
					for(y=row1*50+50, y2=row2*50+50 ; y <= row2*50+50; y += 5, y2 -= 5)
					{
						drawOtherCandys();
						gBuffer.drawImage(cIm2,x,y2,this);
						gBuffer.drawImage(cIm1,x,y,this);
						g.drawImage (virtualMem,0,0,this);
						delay(delay);
					}
					break;
			}
			temp.swapIndexWith(c1);
			c1.swapIndexWith(c2);
			c2.swapIndexWith(temp);
			candys[CI1]=c2;
			candys[CI2]=c1;
			board[row1][col1]=c2;
			board[row2][col2]=c1;

			//markAllMatch();
			//clearMatch(g);

			//repaint();
		}

	}

	public void drop(Graphics g)
	{


		//multithreading for loop
//		for(rowForDrop = maxRow-1; rowForDrop>0; rowForDrop--)
//		{
//			IntStream.range(0, maxCol).parallel().forEach(col->
//		   	{
//		   		int i1 , i2, temp;
//				if(board[rowForDrop][col].getClearable() && !board[rowForDrop-1][col].getClearable())
//				{
//					i1=board[rowForDrop][col].getIndex();
//					i2=board[rowForDrop-1][col].getIndex();
//					//board[row][col].updateImage(candyImages[7]);
//					moveCandy(g, i2, i1, 4, dropSpeed);
//					temp=rowForDrop;
//					while(rowForDrop!=maxRow-1 && board[rowForDrop+1][col].getClearable())
//					{
//						rowForDrop++;
//						i1=board[rowForDrop][col].getIndex();
//						i2=board[rowForDrop-1][col].getIndex();
//						//board[row][col].updateImage(candyImages[7]);
//						moveCandy(g, i2, i1, 4, dropSpeed);
//					}
//					rowForDrop=temp;
//				}
//			});
//		}
//	}
		/////////////////////////////////////////////////////////////////////////////////////////////////////
	/*	for(rowForDrop = maxRow-1; rowForDrop>0; rowForDrop--)
		{
			Parallel.For(0, maxCol, new LoopBody <Integer>()
			{
				public void run(Integer col)
		        {
					int i1 , i2, temp;
					if(board[rowForDrop][col].getClearable() && !board[rowForDrop-1][col].getClearable())
					{
						i1=board[rowForDrop][col].getIndex();
						i2=board[rowForDrop-1][col].getIndex();
						//board[row][col].updateImage(candyImages[7]);
						moveCandy(g, i2, i1, 4, dropSpeed);
						temp=rowForDrop;
						while(rowForDrop!=maxRow-1 && board[rowForDrop+1][col].getClearable())
						{
							rowForDrop++;
							i1=board[rowForDrop][col].getIndex();
							i2=board[rowForDrop-1][col].getIndex();
							//board[row][col].updateImage(candyImages[7]);
							moveCandy(g, i2, i1, 4, dropSpeed);
						}
						rowForDrop=temp;
					}
				}
	       });
		}
*/
		///////////////////////////////////////////////////////////////////////////////////////////////////
//	}


		//normal for loop
//////////////////////////////////////////////////////////////////////////////////////////////
		int i1 , i2, temp;
		for(int row = maxRow-1; row>0; row--)
		{
			for(int col=0; col<maxCol; col++)
			{
				if(board[row][col].getClearable() && !board[row-1][col].getClearable())
				{
					i1=board[row][col].getIndex();
					i2=board[row-1][col].getIndex();
					//board[row][col].updateImage(candyImages[7]);
					moveCandy(g, i2, i1, 4, dropSpeed);
					temp=row;
				while(row!=maxRow-1 && board[row+1][col].getClearable())
					{
						row++;
						i1=board[row][col].getIndex();
						i2=board[row-1][col].getIndex();
						//board[row][col].updateImage(candyImages[7]);
						moveCandy(g, i2, i1, 4, dropSpeed);
					}
					row=temp;
				}
			}
		}

	}
////////////////////////////////////////////////////////////////////////////////////////////


	public boolean needShuffle()
	{
		int type;
		markAllMatch();
		for(int row =0; row<maxRow; row++)
		{
			for(int col=0; col<maxCol; col++)
			{
				if(board[row][col].getClearable())
				{
					System.out.println("there is clearable");
					return false;//no need to shuffle
				}
				if(board[row][col].getType() == 10)
				{
					System.out.println("there is chocolateball");
					return false;
				}
				type = board[row][col].getType();
				if(getTypeIfinBoard(row, col+1) == type)
				{
					if(getTypeIfinBoard(row-1, col-1) == type || getTypeIfinBoard(row+1, col-1) == type
							||getTypeIfinBoard(row, col-2) == type || getTypeIfinBoard(row+1, col+2) == type
							||getTypeIfinBoard(row, col+3) == type || getTypeIfinBoard(row-1, col+2) == type )
					{
						System.out.println("there is ?  ?");
						System.out.println("        ? OO ?");
						System.out.println("         ?  ?");
						return false;
					}
				}
				if(getTypeIfinBoard(row+1, col) == type)
				{
					if(getTypeIfinBoard(row-1, col-1) == type || getTypeIfinBoard(row-1, col+1) == type
							||getTypeIfinBoard(row-2, col) == type || getTypeIfinBoard(row+2, col+1) == type
							||getTypeIfinBoard(row+3, col) == type || getTypeIfinBoard(row+2, col-1) == type )
					{
						System.out.println("there is   ?");
						System.out.println("          ? ?");
						System.out.println("           O ");
						System.out.println("           O  ");
						System.out.println("          ? ?  ");
						System.out.println("           ?");
						return false;
					}
				}
				if(getTypeIfinBoard(row, col+2) == type)
				{
					if(getTypeIfinBoard(row-1, col+1) == type ||getTypeIfinBoard(row+1, col+1) == type)
					{
						System.out.println("there is  ?");
						System.out.println("         O O");
						System.out.println("          ?");
						return false;
					}
				}
				if(getTypeIfinBoard(row+2, col) == type)
				{
					if(getTypeIfinBoard(row+1, col+1) == type ||getTypeIfinBoard(row+1, col-1) == type)
					{
						System.out.println("there is  O");
						System.out.println("         ? ?");
						System.out.println("          O");
						return false;
					}
				}
			}
		}
		return true;

	}

	public void fillRandom(Graphics g)
	{
		Candy c = new Candy();
		int count = 0;
		for(int col=0; col < maxCol; col++)
		{
			if(board[0][col].getClearable())
			{
				count++;
			}

		}
		if(count < 5 && selectedIndex == -1 && selectedIndex2 == -1)
		{
			waitRedraw(g);
		}

		//Parallel.For(0, maxCol, new LoopBody <Integer>()
	    //{
	    //    public void run(Integer i)
	    //   {
		//		if(board[0][i].getClearable())
		//		{
		//			candyType = (int)(Math.random() * numOfColor);
		//			moveCandy(g, candyImages[candyType], i*50+50);
		//			Candy c=new Candy(i, i, 0, candyImages[candyType], candyType);
		//			candys[i] = c;
		//			board[0][i]=c;
		//		}
	    //   }
	    //});

		for(int col=0; col < maxCol; col++)
		{
			if(board[0][col].getClearable())
			{
				candyType = (int)(Math.random() * numOfColor);
				moveCandy(g, candyImages[candyType], col*50+50);
				c=new Candy(col, col, 0, candyImages[candyType], candyType);
				candys[col] = c;
				board[0][col]=c;
			}
		}

		for(int row = maxRow-1; row>0; row--)
		{
			for(int col=0; col<maxCol; col++)
			{
				if(board[row][col].getClearable())
				{
					board[row][col].updateImage(candyImages[7]);
				}
			}
		}
		//g.drawImage(virtualMem,0,0,this);
		//repaint();
	}



	public int swapCase(int CI1, int CI2)
	{
		int colDifference, rowDifference;
		if (CI1 != -1 && CI2 != -1)
		{
			colDifference = candys[CI1].getCol()- candys[CI2].getCol();
			rowDifference = candys[CI1].getRow()- candys[CI2].getRow();
			//System.out.println("colDiff "+colDifference+" rowDiff "+rowDifference);
			//System.out.println("col1 "+candys[CI1].getCol()+" row1 "+candys[CI1].getRow());
			//System.out.println("col2 "+candys[CI2].getCol()+" row2 "+candys[CI2].getRow());
			if(colDifference == 0 && rowDifference == 1)
				return 3; //Candy 1 to the left-->up
			else
				if(colDifference == 0 && rowDifference == -1)
					return 4; //Candy 1 to the right-->down
				else
					if(colDifference == 1 && rowDifference == 0)
						return 1; //Candy 1 go down-->left
					else
						if(colDifference == -1 && rowDifference == 0)
							return 2; //Candy 1 go up-->right
		}
		return -1; //Candy 1 and Candy 2 are not adjacent
	}

	public int[] countRowColor(int type)
	{
		int count;
		int[] rowColor = new int[maxRow];
		for(int row=0; row<maxRow; row++)
		{
			count=0;
			for(int col =0; col<maxCol; col++)
			{
				if(board[row][col].getType()==type)
				{
					count++;
				}

			}
			rowColor[row]=count;
			//System.out.println("Row "+ row + " "+ count);
		}
		return rowColor;
	}

	public int[] countColColor(int type)
	{
		int count;
		int[] colColor = new int[maxCol];
		for(int c=0; c<maxCol; c++)
		{
			count=0;
			for(int r =0; r<maxRow; r++)
			{
				if(board[r][c].getType()==type)
				{
					count++;
				}

			}
			colColor[c]=count;
		}
		return colColor;
	}

	public void markMatchHorizontal(int type)
	{
		int[] row = countRowColor(type);
		for(int k=0; k < row.length; k++)
		{
			if (row[k] >= 3)
			{
				markThreeOrMore(k, type);
			}
		}

	}
	public void markMatchVertical(int type)
	{
		int[] col = countColColor(type);
		for(int k=0; k < col.length; k++)
		{
			if (col[k] >= 3)
			{
				markThreeOrMoreVertical(k, type);
			}
		}
	}
	public void markThreeOrMoreVertical(int col, int type)
	{
		int row = 0;
		while(row < maxRow-2)
		{
			if(board[row][col].getType()==type && board[row][col].getSpecialCase() != 0)
			{
				row++;
				if(board[row][col].getType()==type && board[row][col].getSpecialCase() != 0)
				{
					row++;
					if(board[row][col].getType()==type && board[row][col].getSpecialCase() != 0)
					{
						board[row][col].clearableTrue();
						board[row-1][col].clearableTrue();
						board[row-2][col].clearableTrue();
						row++;
						while(row < maxRow && board[row][col].getType()==type && board[row][col].getSpecialCase() != 1)
						{

							board[row][col].clearableTrue();
							row++;
						}
					}
				}
			}
		row++;
		}
	}
	//helper
	public void markThreeOrMore(int row, int type)
	{
		int col = 0;
		while(col < maxCol-2)
		{
			if(board[row][col].getType()==type && board[row][col].getSpecialCase() != 0)
			{
				col++;
				if(board[row][col].getType()==type && board[row][col].getSpecialCase() != 0)
				{
					col++;
					if(board[row][col].getType()==type && board[row][col].getSpecialCase() != 0)
					{
						board[row][col].clearableTrue();
						board[row][col-1].clearableTrue();
						board[row][col-2].clearableTrue();
						col++;
						while(col < maxCol && board[row][col].getType()==type && board[row][col].getSpecialCase() != 1)
						{

							board[row][col].clearableTrue();
							col++;
						}
					}
				}
			}
		col++;
		}
	}

	public void markChocolatePosition()
	{
		for(int type = 0; type < numOfColor; type++)
		{
			int[] colCount = countColColor(type);
			for(int col=0; col < colCount.length; col++)
			{
				if (colCount[col] >= 5)
				{
					int row = 0;
					while(row < maxRow-4)
					{
						if(board[row][col].getType()==type)
						{
							row++;
							if(board[row][col].getType()==type)
							{
								row++;
								if(board[row][col].getType()==type)
								{
									row++;
									if(board[row][col].getType()==type)
									{
										row++;
										if(board[row][col].getType()==type)
										{
											board[row-2][col].setChocolate(candyImages[10]);
											candys[board[row-2][col].getIndex()].setChocolate(candyImages[10]);
											board[row-3][col].clearableTrue();
											board[row-4][col].clearableTrue();
											board[row-1][col].clearableTrue();
											board[row][col].clearableTrue();
										}
									}
								}
							}
						}
					row++;
					}
				}
			}




			int[] rowCount = countRowColor(type);
			for(int row=0; row < rowCount.length; row++)
			{

				if (rowCount[row] >= 5)
				{
					int col = 0;
					while(col < maxCol-4)
					{
						if(board[row][col].getType()==type)
						{
							col++;
							if(board[row][col].getType()==type)
							{
								col++;
								if(board[row][col].getType()==type)
								{
									col++;
									if(board[row][col].getType()==type)
									{
										col++;
										if(board[row][col].getType()==type)
										{
											board[row][col-2].setChocolate(candyImages[10]);
											candys[board[row][col-2].getIndex()].setChocolate(candyImages[10]);
											board[row][col].clearableTrue();
											board[row][col-1].clearableTrue();
											board[row][col-3].clearableTrue();
											board[row][col-4].clearableTrue();
										}
									}
								}
							}
						}
					col++;
					}
				}
			}

		}
	}

	public void clearMatch(Graphics g)
	{
		int index; //x, y

		for(int row =0; row<maxRow; row++)
		{
			for(int col=0; col<maxCol; col++)
			{

				if(board[row][col].getClearable() == true && board[row][col].getSpecialCase() == 1 || board[row][col].getSpecialCase() == 0)
				{

					for(int r= row-1; r <= row+1; r++)
						for(int c = col-1; c <= col+1; c++)
						{
								markSingle(r,c);
								delay(10);
						}
					board[row][col].clearableFalse();
					board[row][col].setSpecialCase(2);
				}
				else
					if(board[row][col].getClearable() == true && board[row][col].getSpecialCase() == 5)
					{

						for(int r=0; r < maxRow; r++)
						{
							markSingle(r,col);
							delay(10);
						}
					}
					else
						if(board[row][col].getClearable() == true && board[row][col].getSpecialCase() == 6)
						{

							for(int c=0; c < maxCol; c++)
							{
								markSingle(row,c);
								delay(10);
							}
						}
						//else
						//	if(board[row][col].getSpecialCase() == 3)
						//	{
						//		board[row][col].setSpecialCase(4);
						//	}
							else
								if(board[row][col].getSpecialCase() == 3)
								{
									for(int r = row-2; r <= row+2; r++)
										for(int c = col-2; c <= col+2; c++)
										{
											markSingle(r,c);
										}
								}
								else
									if(board[row][col].getSpecialCase() == 2)
									{
										for(int r = row-1; r <= row+1; r++)
											for(int c = col-1; c <= col+1; c++)
											{
												markSingle(r,c);
											}

									}
			}
		}



		for(int row =0; row<maxRow; row++)
		{
			for(int col=0; col<maxCol; col++)
			{
				if(board[row][col].getClearable() == true)
				{
					index = col+row*maxCol;
					//x=col*50+50;
					//y=row*50+50;
					//paintCandy(g);
					//delay(30);
					//gBuffer.drawImage(candyImages[6],x,y,this);
					//candys[index].setInvalidType();
					//board[row][col].setInvalidType();
					candys[index].updateImage(candyImages[7]);
					board[row][col].updateImage(candyImages[7]);

					if(!board[row][col].getScored())
					{
						candys[index].updateImage(candyImages[6]);
						board[row][col].updateImage(candyImages[6]);
						board[row][col].scored();
						candys[index].scored();
						score += 60;
					}
					g.drawImage (virtualMem,0,0,this);
				}


			}
		}

	}


	public void drawOtherCandys()
	{
		int x,y;

		createBackGround();
		for(int l=0; l<candys.length;l++)
		{
			if(l!=selectedIndex && l!=selectedIndex2)
			{
				x=(l%maxCol)*50+50;
				y=(l/maxCol)*50+50;
				gBuffer.drawImage(candys[l].getImage(), x, y, this);
			}
		}
	}
	public void drawOtherCandys2()
	{
		int x,y;

		createBackGround();
		for(int l=0; l<candys.length;l++)
		{
				x=(l%maxCol)*50+50;
				y=(l/maxCol)*50+50;
				gBuffer.drawImage(candys[l].getImage(), x, y, this);
			if(l==selectedIndex)
			{
				gBuffer.setColor(Color.red);
				gBuffer.drawRect((int)candyBlocks[selectedIndex].getX(),(int)candyBlocks[selectedIndex].getY(),49,49);
			}
		}
	}

	public void createBackGround()//or draw Background
	{
		gBuffer.setColor(Color.white);
		//gBuffer.fillRect(0,0,appletWidth, appletHeight);
		gBuffer.drawImage(getImage(getDocumentBase(), "background.png"),0,0,this);
		//gBuffer.setColor(Color.white);
		//gBuffer.fillRect(0,0,1024,50);
		gBuffer.drawImage(otherImages[0],maxCol*50+100, 2*50+50, this);
		gBuffer.drawImage(otherImages[1],maxCol*50+75, 50, this);
		gBuffer.setColor(new Color(78,89,59));
		gBuffer.setFont(new Font("Brush Script MT", Font.BOLD, 42));
		gBuffer.drawString(""+moveLeft,maxCol*50+165,130);
		gBuffer.setFont(new Font("Brush Script MT", Font.BOLD, 36));
		gBuffer.drawString(""+score,maxCol*50+140,2*50+170);
	}

	public void delay(double n)
	{
		long startDelay = System.currentTimeMillis();
		long endDelay = 0;
		while (endDelay - startDelay < n)
			endDelay = System.currentTimeMillis();
	}

}

