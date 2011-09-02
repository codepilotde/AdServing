package net.mad.ads.base.api.track.events;

public class ImpressionTrackEvent extends TrackEvent {
	public ImpressionTrackEvent () {
		super();
		
		put(EventAttribute.TYPE, EventType.IMPRESSION.getName());
	}
}
