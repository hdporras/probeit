package probeIt.viewerFramework.transform;

import pml.impl.serializable.PMLConclusion;

public interface ConclusionTransformer 
{
	public abstract Object transform(PMLConclusion conclusion);
	public abstract String getName();
}
