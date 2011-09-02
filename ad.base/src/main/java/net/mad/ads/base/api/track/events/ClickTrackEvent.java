package net.mad.ads.base.api.track.events;

public class ClickTrackEvent extends TrackEvent {
	public ClickTrackEvent () {
		super();
		
		put(EventAttribute.TYPE, EventType.CLICK.getName());
	}
}
