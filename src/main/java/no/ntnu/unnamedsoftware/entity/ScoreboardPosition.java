package no.ntnu.unnamedsoftware.entity;

public class ScoreboardPosition {
	

    private Long scoreboardId;
    private int points;
    private int position;
	private Russ russId;


    public ScoreboardPosition(Long long1, int points, int position, Russ russId) 
    {
        this.scoreboardId = long1;
        this.points = points;
        this.position = position;
        this.russId = russId;
    }
    
    public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

    public Long getScoreboardId() {
        return scoreboardId;
    }

    public void setScoreboardId(Long scoreboardId) {
        this.scoreboardId = scoreboardId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Russ getRussId() {
        return russId;
    }

    public void setRussId(Russ russId) {
        this.russId = russId;
    }

}
