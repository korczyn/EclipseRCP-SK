package com.starterkit.views;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.property.Properties;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.part.ViewPart;

import com.starterkit.views.model.ModelProvider;
import com.starterkit.views.model.Person;
import com.starterkit.views.model.Task;

public class ArchiveView extends ViewPart {

	private TableViewer viewer;

	public ArchiveView() {
	}
	
	public ViewPart getView(){
		return this;
	}

	@Override
	public void createPartControl(Composite parent) {
		GridLayout layout = new GridLayout(2, false);
		parent.setLayout(layout);
		createViewer(parent);
	}

	public void refresh() {
		viewer.refresh();
	}

	private void createViewer(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		createColumns(parent, viewer);
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		viewer.setContentProvider(ArrayContentProvider.getInstance());
		IObservableList input = 
				   Properties.selfList(Person.class).observe(ModelProvider.INSTANCE.getArchive());
		viewer.setInput(input);

		getSite().setSelectionProvider(viewer);
		
		
		MenuManager manager = new MenuManager();
		viewer.getControl().setMenu(
				manager.createContextMenu(viewer.getControl()));
		manager.add(new Action("Revert from archive") {
			@Override
			public void run() {
				IStructuredSelection selection = (IStructuredSelection) viewer
						.getSelection();
				
				Object t = selection.getFirstElement();
				if (t instanceof Task){
					Task task = (Task) t;
					ModelProvider.INSTANCE.sendToTasks(task);
				}
				refresh();
			}
		});
		manager.add(new Action("Refresh") {
			@Override
			public void run() {
				refresh();
			}
		});

		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		viewer.getControl().setLayoutData(gridData);

	}

	public TableViewer getViewer() {
		return viewer;
	}

	private void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { "Id", "Name", "Status", "Due date", "Info" };
		int[] bounds = { 50, 200, 100, 100, 200 };

		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Task t = (Task) element;
				return t.getId().toString();
			}
		});

		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Task t = (Task) element;
				return t.getName();
			}
		});

		col = createTableViewerColumn(titles[2], bounds[2], 2);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Task t = (Task) element;
				return t.getStatus();
			}
		});

		col = createTableViewerColumn(titles[3], bounds[3], 3);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Task t = (Task) element;
				return t.getDate();
			}
		});

		col = createTableViewerColumn(titles[4], bounds[4], 4);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Task t = (Task) element;
				return t.getInfo();
			}
		});
	}

	private TableViewerColumn createTableViewerColumn(String title, int bound,
			int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,
				SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}

	@Override
	public void setFocus() {
		refresh();
	}

}
