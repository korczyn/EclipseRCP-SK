package com.starterkit.views;

import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
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

import com.starterkit.views.model.SortBy;
import com.starterkit.views.model.Status;
import com.starterkit.views.model.Task;
import com.starterkit.views.provider.ModelProvider;

public class TaskView extends ViewPart {

	public static final String ID = "com.starterkit.views.ArchiveView";
	private TableViewer viewer;

	public TaskView() {
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
		viewer = new TableViewer(parent, SWT.SINGLE | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		createColumns(parent, viewer);
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		viewer.setContentProvider(ArrayContentProvider.getInstance());
		WritableList input = new WritableList(
				ModelProvider.INSTANCE.getTasks(), Task.class);
		ViewerSupport.bind(
				viewer,
				input,
				BeanProperties.values(new String[] { "id", "name", "status",
						"date", "info" }));

		viewer.setInput(input);
		getSite().setSelectionProvider(viewer);

		MenuManager manager = fillMenu();
		viewer.getControl().setMenu(
				manager.createContextMenu(viewer.getControl()));

		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		viewer.getControl().setLayoutData(gridData);
	}

	public MenuManager fillMarkAsSubmenu() {
		MenuManager subMenu = new MenuManager("Mark as ...");
		subMenu.add(new Action("Not started") {
			@Override
			public void run() {
				IStructuredSelection selection = (IStructuredSelection) viewer
						.getSelection();
				Object o = selection.getFirstElement();
				if (o instanceof Task) {
					Task t = (Task) o;
					t.setStatus(Status.NOT_STARTED);
					ModelProvider.INSTANCE.changeTask(t);
					refresh();
				}
			}
		});
		subMenu.add(new Action("In Progress") {
			@Override
			public void run() {
				IStructuredSelection selection = (IStructuredSelection) viewer
						.getSelection();
				Object o = selection.getFirstElement();
				if (o instanceof Task) {
					Task t = (Task) o;
					t.setStatus(Status.IN_PROGRESS);
					ModelProvider.INSTANCE.changeTask(t);
					refresh();
				}
			}
		});
		subMenu.add(new Action("Canceled") {
			@Override
			public void run() {
				IStructuredSelection selection = (IStructuredSelection) viewer
						.getSelection();
				Object o = selection.getFirstElement();
				if (o instanceof Task) {
					Task t = (Task) o;
					t.setStatus(Status.CANCELED);
					ModelProvider.INSTANCE.changeTask(t);
					refresh();
				}
			}
		});
		subMenu.add(new Action("Failed") {
			@Override
			public void run() {
				IStructuredSelection selection = (IStructuredSelection) viewer
						.getSelection();
				Object o = selection.getFirstElement();
				if (o instanceof Task) {
					Task t = (Task) o;
					t.setStatus(Status.FAILED);
					ModelProvider.INSTANCE.changeTask(t);
					refresh();
				}
			}
		});
		subMenu.add(new Action("Done") {
			@Override
			public void run() {
				IStructuredSelection selection = (IStructuredSelection) viewer
						.getSelection();
				Object o = selection.getFirstElement();
				if (o instanceof Task) {
					Task t = (Task) o;
					t.setStatus(Status.DONE);
					ModelProvider.INSTANCE.changeTask(t);
					refresh();
				}
			}
		});
		return subMenu;
	}

	public MenuManager fillSortMenu() {
		MenuManager subMenu = new MenuManager("Sort by ...");
		subMenu.add(new Action("ID") {
			@Override
			public void run() {
				ModelProvider.INSTANCE.sortBy(SortBy.ID);
				refresh();
			}
		});
		subMenu.add(new Action("Name") {
			@Override
			public void run() {
				ModelProvider.INSTANCE.sortBy(SortBy.NAME);
				refresh();
			}
		});
		subMenu.add(new Action("Status") {
			@Override
			public void run() {
				ModelProvider.INSTANCE.sortBy(SortBy.STATUS);
				refresh();
			}
		});
		return subMenu;
	}

	public MenuManager fillMenu() {
		MenuManager manager = new MenuManager();
		viewer.getControl().setMenu(
				manager.createContextMenu(viewer.getControl()));

		manager.add(new Action("Send to archive") {
			@Override
			public void run() {
				IStructuredSelection selection = (IStructuredSelection) viewer
						.getSelection();
				Object o = selection.getFirstElement();
				if (o instanceof Task) {
					Task t = (Task) o;
					ModelProvider.INSTANCE.sendToArchive(t);
					refresh();
				}
			}
		});
		MenuManager markAsSubmenu = fillMarkAsSubmenu();
		manager.add(markAsSubmenu);
		MenuManager sortSubmenu = fillSortMenu();
		manager.add(sortSubmenu);

		return manager;
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
