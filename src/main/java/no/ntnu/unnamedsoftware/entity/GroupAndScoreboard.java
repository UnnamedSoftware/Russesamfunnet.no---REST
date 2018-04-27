package no.ntnu.unnamedsoftware.entity;

import java.util.List;

public class GroupAndScoreboard {
	private Group group;
	private List<ScoreboardPosition> scoreboard;
	
	public GroupAndScoreboard(Group group, List<ScoreboardPosition> scoreboard)
	{
		this.group = group;
		this.scoreboard = scoreboard;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public List<ScoreboardPosition> getScoreboard() {
		return scoreboard;
	}

	public void setScoreboard(List<ScoreboardPosition> scoreboard) {
		this.scoreboard = scoreboard;
	}

}
