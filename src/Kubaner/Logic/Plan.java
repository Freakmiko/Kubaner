package Kubaner.Logic;

import java.util.Date;

public class Plan {
	private Date startTime;
	private TimeLine timeline[];
	
	Plan(Date startTime) {
		this.startTime = startTime;
		this.timeline = new TimeLine[0];
	}
	
	
	/**
	 * Extends the timeline-array by 1 and adds a new timeline in the new last field.
	 * @param newTimeLine - timeline to be added.
	 */
	public void add(TimeLine newTimeline) {
		TimeLine oldTimeLine[] = this.timeline;
		
		this.timeline = new TimeLine[this.timeline.length + 1];
		
		for(int i = 0; i < oldTimeLine.length; i++) {
			this.timeline[i] = oldTimeLine[i];
		}
		
		this.timeline[oldTimeLine.length] = newTimeline;
	}
	
	
	/**
	 * Returns the number of timelines stored in this plan.
	 * @return the number of timelines stored in this plan.
	 */
	public int getTimeLineNumber() {
		return this.timeline.length;
	}
	
	
	/**
	 * Returns the timeline stored at the argument index.
	 * @return TimeLine the timeline stored at the argument index.
	 */
	public TimeLine getTimeLine(int index) {
		return this.timeline[index];
	}
}
