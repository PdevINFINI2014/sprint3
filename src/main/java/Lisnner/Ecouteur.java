package Lisnner;



import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

@SuppressWarnings("serial")
public class Ecouteur implements PhaseListener {

	@Override
	public void afterPhase(PhaseEvent event) {
		System.out.println(" END Phase :" + event.getPhaseId().toString() );
		
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		System.out.println(" Star Phase :" + event.getPhaseId().toString() );
		
	}

	@Override
	public PhaseId getPhaseId() {
	
		return PhaseId.ANY_PHASE;
	}

}
