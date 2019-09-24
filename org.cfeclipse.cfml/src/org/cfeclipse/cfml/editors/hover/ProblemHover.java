package org.cfeclipse.cfml.editors.hover;

import java.util.ArrayList;

import org.eclipse.core.resources.IMarker;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.texteditor.MarkerAnnotation;



/**
 * This annotation hover shows the description of the
 * selected CFML annotation.
 */
public class ProblemHover extends AbstractAnnotationHover {

	protected static class ProblemInfo extends AnnotationInfo {

		private static final ICompletionProposal[] NO_PROPOSALS= new ICompletionProposal[0];

		public ProblemInfo(Annotation annotation, Position position, ITextViewer textViewer) {
			super(annotation, position, textViewer);
		}

		/*
		 * @see org.eclipse.jdt.internal.ui.text.java.hover.AbstractAnnotationHover.AnnotationInfo#getCompletionProposals()
		 */
		@Override
		public ICompletionProposal[] getCompletionProposals() {
//			if (annotation instanceof ICFMLAnnotation) {
//				ICompletionProposal[] result= getCFMLAnnotationFixes((ICFMLAnnotation) annotation);
//				if (result.length > 0)
//					return result;
//			}

			if (annotation instanceof MarkerAnnotation)
				return getMarkerAnnotationFixes((MarkerAnnotation) annotation);

			return NO_PROPOSALS;
		}
/*
		private ICompletionProposal[] getCFMLAnnotationFixes(ICFMLAnnotation javaAnnotation) {
			ProblemLocation location= new ProblemLocation(position.getOffset(), position.getLength(), javaAnnotation);
			ICompilationUnit cu= javaAnnotation.getCompilationUnit();
			if (cu == null)
				return NO_PROPOSALS;

			ISourceViewer sourceViewer= null;
			if (viewer instanceof ISourceViewer)
				sourceViewer= (ISourceViewer) viewer;

			IInvocationContext context= new AssistContext(cu, sourceViewer, location.getOffset(), location.getLength(), SharedASTProvider.WAIT_ACTIVE_ONLY);
			if (!SpellingAnnotation.TYPE.equals(javaAnnotation.getType()) && !hasProblem(context.getASTRoot().getProblems(), location))
				return NO_PROPOSALS;

			ArrayList<ICFMLCompletionProposal> proposals= new ArrayList<>();
			CFMLCorrectionProcessor.collectCorrections(context, new IProblemLocation[] { location }, proposals);
			Collections.sort(proposals, new CompletionProposalComparator());

			return proposals.toArray(new ICompletionProposal[proposals.size()]);
		}

		private static boolean hasProblem(IProblem[] problems, IProblemLocation location) {
			for (int i= 0; i < problems.length; i++) {
				IProblem problem= problems[i];
				if (problem.getID() == location.getProblemId() && problem.getSourceStart() == location.getOffset())
					return true;
			}
			return false;
		}
*/
		private ICompletionProposal[] getMarkerAnnotationFixes(MarkerAnnotation markerAnnotation) {
			if (markerAnnotation.isQuickFixableStateSet() && !markerAnnotation.isQuickFixable())
				return NO_PROPOSALS;

			IMarker marker = markerAnnotation.getMarker();
			ArrayList<ICompletionProposal> proposals = new ArrayList<ICompletionProposal>();
			IMarkerResolution[] resolutions = null;
			resolutions = IDE.getMarkerHelpRegistry().getResolutions(marker);
			for (IMarkerResolution resolution : resolutions) {
				proposals.add(new org.cfeclipse.cflint.quickfix.MarkerResolutionProposal(resolution, marker));
			}
			return proposals.toArray(new ICompletionProposal[proposals.size()]);
		}

		/*
		private static ICompilationUnit getCompilationUnit(IMarker marker) {
			IResource res= marker.getResource();
			if (res instanceof IFile && res.isAccessible()) {
				ICFMLElement element= CFMLCore.create((IFile) res);
				if (element instanceof ICompilationUnit)
					return (ICompilationUnit) element;
			}
			return null;
		}
*/
	}

	public ProblemHover() {
		super(false);
	}

	@Override
	protected AnnotationInfo createAnnotationInfo(Annotation annotation, Position position, ITextViewer textViewer) {
		return new ProblemInfo(annotation, position, textViewer);
	}
}