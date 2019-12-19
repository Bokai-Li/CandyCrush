import java.awt.*;

public class Candy
{

	private int index, colIndex, rowIndex, type, specialCase;
	private boolean clearable, scored, shouldBomb, processBomb;
	private Image cImage;

	public Candy()
	{
	}

    public Candy(int i, int c, int r, Image im, int tp)
    {
		index=i;
		colIndex=c;
		rowIndex=r;
		cImage=im;
		type=tp;
		clearable=false;
		scored=false;
		specialCase=-1;
		shouldBomb=false;
		processBomb=false;
    }

    public Image getImage()
    {
    	return cImage;
    }

    public int getCol()
    {
 		return colIndex;
    }

	public int getRow()
	{
		return rowIndex;
	}
	public int getIndex()
	{
		return index;
	}
	public int getType()
	{
		return type;
	}
	public boolean getClearable()
	{
		return clearable;
	}
	public void updateImage(Image i)
	{
		this.type=-1;
		this.cImage = i;
	}
	public void swapIndexWith(Candy c)
	{
		this.index=c.index;
		this.colIndex=c.colIndex;
		this.rowIndex=c.rowIndex;
	}
	public void clearableTrue()
	{
		this.clearable = true;
	}
	public void clearableFalse()
	{
		this.clearable = false;
	}
	public void scored()
	{
		this.scored = true;
	}
	public boolean getScored()
	{
		return scored;
	}
	public void setInvalidType()
	{
		this.type = -1;
	}
	public void setChocolate(Image chocoImage)
	{
		type = 10;
		cImage = chocoImage;
		clearable = false;
	}

	public void setVStriped(Image VS)
	{
		specialCase = 5;//vertical
		cImage = VS;
		clearable = false;
	}
	public void setHStriped(Image HS)
	{
		specialCase = 6;//horizontal
		cImage = HS;
		clearable = false;
	}

	public void setBomb(Image bombImage)
	{
		specialCase=1;
		cImage = bombImage;
		clearable = false;
	}
	public void bombNextRound()
	{
		specialCase=0;
	}
	public int getSpecialCase()
	{
		return specialCase;
	}
	public void setSpecialCase(int c)
	{
		specialCase=c;
	}

	public void shouldBomb()
	{
		shouldBomb=true;
		clearable=true;
	}
	public Boolean getShouldBomb()
	{
		return shouldBomb;
	}
	public void processB()
	{
		processBomb = true;
	}
	public void resetProcessB()
	{
		processBomb = false;
	}
	public Boolean getProcessBomb()
	{
		return processBomb;
	}
	public void setNormal()
	{
		specialCase=-1;
	}

}
