package org.topcased.pickerexplorer.ui.pickers.javaqueries;
//package org.topcased.pickerexplorer.ui.javaqueries;
//
//import java.util.Collections;
//import java.util.LinkedList;
//import java.util.List;
//import org.eclipse.core.runtime.IAdaptable;
//import org.eclipse.core.runtime.Platform;
//import org.eclipse.emf.common.util.URI;
//import org.eclipse.emf.ecore.EObject;
//import org.eclipse.emf.ecore.resource.ResourceSet;
//import org.eclipse.emf.query.index.Index;
//import org.eclipse.emf.query.index.IndexFactory;
//import org.eclipse.emf.query.index.query.EReferenceQuery;
//import org.eclipse.emf.query.index.query.IndexQueryFactory;
//import org.eclipse.emf.query.index.query.QueryCommand;
//import org.eclipse.emf.query.index.query.QueryExecutor;
//import org.eclipse.emf.query.index.query.QueryResult;
//import org.eclipse.emf.query.index.query.descriptors.EReferenceDescriptor;
//import org.topcased.iterators.configurations.pickers.annotations.Argument;
//import org.topcased.iterators.configurations.pickers.annotations.IJavaQuery;
//import org.topcased.pickerexplorer.ui.Messages;
//
//
//public class MQ2InverseReferencesQuery implements IJavaQuery {
//
//	private final static String INDEXED = "indexed";
//	private final static String LOADED = "loaded";
//	private final static String CURRENT = "current";
//
//	@Argument(label = "Select the scope of the query (workspace, loaded or current)", choices = {INDEXED, LOADED, CURRENT})
//	private String scope = INDEXED;
//	
//	@Argument(label = "Wether to search within the scope or outside of it")
//	private boolean inclusiveScope = true;
//
//	private ResourceSet rs;
//	private ScopeValidator scopeValidator;
//
//
//	@Override
//	public Iterable<?> getNexts(Object element) {
//
//		final EObject eo = getEObject(element);
//		if (eo == null)
//		{
//			return Collections.EMPTY_SET;
//		}
//
//		EReferenceQuery<EReferenceDescriptor> refQuery = createQuery(eo);
//		rs = eo.eResource().getResourceSet();
//		
//		if (scope.equals(CURRENT))
//		{
//			scopeValidator = new ScopeValidator() {
//
//				@Override
//				public boolean isInScope(URI uri) {
//					return uri.equals(eo.eResource().getURI());
//				};
//
//			};
//		}
//		else if (scope.equals(LOADED))
//		{
//			scopeValidator = new ScopeValidator() {
//
//				@Override
//				public boolean isInScope(URI uri) {
//					return rs.getResource(uri, false).isLoaded();
//				}
//
//			};
//			
//		}
//		else if (scope.equals(INDEXED)) {
//			scopeValidator = new ScopeValidator();
//		}
//
//
//
//		return executeQuery(refQuery);
//	}
//
//	private EReferenceQuery<EReferenceDescriptor> createQuery(EObject eo) {
//		EReferenceQuery<EReferenceDescriptor> refQuery = IndexQueryFactory.createEReferenceQuery();
//		String targerResourceURI = eo.eResource().getURI().toString();
//		String targetFragment = eo.eResource().getURIFragment(eo);
//		refQuery.targetEObject().resource().uri(targerResourceURI);
//		refQuery.targetEObject().fragment(targetFragment);
//		return refQuery;
//	}
//
//	private List<EObject> executeQuery(final EReferenceQuery<EReferenceDescriptor> refQuery) {
//		Index index = IndexFactory.getInstance();
//		final List<EObject> results = new LinkedList<EObject>();
//		index.executeQueryCommand(new QueryCommand() {
//
//			@Override
//			public void execute(QueryExecutor executor) {
//				QueryResult<EReferenceDescriptor> queryResult = executor.execute(refQuery);
//				for (EReferenceDescriptor desc : queryResult) {
//					if (scopeValidator.isInScope(desc.getSourceResourceURI()) == inclusiveScope) {
//						URI sourceURI = desc.getSourceResourceURI().appendFragment(desc.getSourceFragment());
//						results.add(rs.getEObject(sourceURI, false));
//					}
//				}
//			}
//		});
//		return results;
//	}
//
//	private EObject getEObject(Object element) {
//		EObject eo = null;
//		if (element instanceof EObject)
//		{
//			eo = (EObject) element;
//		}
//		if (eo == null)
//		{
//			if (element instanceof IAdaptable)
//			{
//				IAdaptable adaptable = (IAdaptable) element;
//				eo = (EObject) adaptable.getAdapter(EObject.class);
//			}
//			if (eo == null)
//			{
//				Platform.getAdapterManager().getAdapter(element, EObject.class);
//			}
//		}
//
//		return eo;
//	}
//
//	/**
//	 * Used to determine wether an URI is within the scope or not. Default
//	 * is true (the entire indexed resources).
//	 * @author mgrihang
//	 *
//	 */
//	private class ScopeValidator {
//		public boolean isInScope(URI uri) {
//			return true;
//		}
//	}
//
//}
