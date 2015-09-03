package com.starterkit.views;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;

import com.starterkit.views.model.ModelProvider;
import com.starterkit.views.model.Task;
import java.util.ResourceBundle;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DND;

public class EditView extends ViewPart {
	private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("com.starterkit.views.messages"); //$NON-NLS-1$

	public static final String ID = "com.starterkit.views.TaskView";
	private Task t;
	private Text idField;
	private Text nameField;
	private Text infoField;
	private Combo statusCombo;
	private DateTime dateTime;

	ISelectionListener listener = new ISelectionListener() {

		@Override
		public void selectionChanged(IWorkbenchPart part, ISelection selection) {
			IStructuredSelection ss = (IStructuredSelection) selection;
			t = (Task) ss.getFirstElement();
			if (t != null) {
				idField.setText(t.getId().toString());
				nameField.setText(t.getName());
				statusCombo.setText(t.getStatus());
				infoField.setText(t.getInfo());
				setDate(t.getDate());
			}
		}
	};

	public void dispose() {
		getSite().getPage().removeSelectionListener(listener);
	}

	public void setDate(String date) {
		String[] times = date.split("/");
		for (int i = 0; i < times.length; i++) {
			times[i] = times[i].replaceFirst("^0+(?!$)", "");
		}

		dateTime.setDay(Integer.parseInt(times[0]));
		dateTime.setMonth(Integer.parseInt(times[1]) - 1);
		dateTime.setYear(Integer.parseInt(times[2]));
	}

	private String dateToString() {
		int day = dateTime.getDay();
		int month = dateTime.getMonth() + 1;
		int year = dateTime.getYear();

		String strDate = (day < 10) ? "0" + day + "/" : day + "/";
		strDate += (month < 10) ? "0" + month + "/" : month + "/";
		strDate += year;
		return strDate;
	}

	public EditView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(3, false));

		Label lblNewLabel_2 = new Label(parent, SWT.NONE);
		lblNewLabel_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblNewLabel_2.setText(BUNDLE.getString("label.id")); //$NON-NLS-1$

		idField = new Text(parent, SWT.BORDER | SWT.READ_ONLY);
		idField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				2, 1));

		Label lblNewLabel = new Label(parent, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblNewLabel.setText(BUNDLE.getString("label.name")); //$NON-NLS-1$

		nameField = new Text(parent, SWT.BORDER);
		nameField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				2, 1));

		Label lblDueDate = new Label(parent, SWT.NONE);
		lblDueDate.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblDueDate.setText(BUNDLE.getString("label.date")); //$NON-NLS-1$

		dateTime = new DateTime(parent, SWT.BORDER);
		new Label(parent, SWT.NONE);

		Label lblNewLabel_1 = new Label(parent, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblNewLabel_1.setText(BUNDLE.getString("label.status")); //$NON-NLS-1$

		statusCombo = new Combo(parent, SWT.READ_ONLY);
		statusCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 2, 1));
		statusCombo.add("", 0);
		statusCombo.add("Done", 1);
		statusCombo.add("Failed", 2);
		statusCombo.add("Canceled", 3);
		statusCombo.add("In progress", 4);
		statusCombo.add("Not started", 5);
		
				Label lblInfo = new Label(parent, SWT.NONE);
				lblInfo.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false,
						1, 1));
				lblInfo.setText(BUNDLE.getString("label.info"));
		
				infoField = new Text(parent, SWT.BORDER);
				infoField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
						1, 1));
		new Label(parent, SWT.NONE);
		
				Button btnSaveChanges = new Button(parent, SWT.NONE);
				btnSaveChanges.setToolTipText(BUNDLE.getString("EditView.btnSaveChanges.toolTipText")); //$NON-NLS-1$
				btnSaveChanges.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
				btnSaveChanges.setText(BUNDLE.getString("button.save")); //$NON-NLS-1$
				btnSaveChanges.addSelectionListener(new SelectionListener() {

					@Override
					public void widgetSelected(SelectionEvent e) {
						if (isEverythingFilled()) {
							Task t = new Task(Long.valueOf(idField.getText()),
									nameField.getText(), statusCombo.getText(),
									dateToString(), infoField.getText());
							ModelProvider.INSTANCE.changeTask(t);
							clearFields();
						} else {
							MessageDialog.openError(null, "Error", "No task selected");
						}
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
					}
				});
		
				Button btnAddTask = new Button(parent, SWT.NONE);
				btnAddTask.setToolTipText(BUNDLE.getString("EditView.btnAddTask.toolTipText")); //$NON-NLS-1$
				btnAddTask.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
				btnAddTask.setText(BUNDLE.getString("button.add")); //$NON-NLS-1$
				btnAddTask.addSelectionListener(new SelectionListener() {

					@Override
					public void widgetSelected(SelectionEvent e) {
						if (isEverythingFilled()) {
							Task task = new Task(null, nameField.getText(), statusCombo
									.getText(), dateToString(), infoField.getText());
							ModelProvider.INSTANCE.addTask(task);
							TaskView part = (TaskView) getViewSite().getPage()
									.findView(EditView.ID);
							part.refresh();
							clearFields();
						} else {
							MessageDialog.openError(null, "Error", "Fill all fields");
						}
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
					}
				});

		Button btnDeleteTask = new Button(parent, SWT.NONE);
		btnDeleteTask.setToolTipText(BUNDLE.getString("EditView.btnDeleteTask.toolTipText")); //$NON-NLS-1$
		btnDeleteTask.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnDeleteTask.setText(BUNDLE.getString("button.delete")); //$NON-NLS-1$
		btnDeleteTask.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (idField.getText().equals("")) {
					MessageDialog
							.openError(null, "Error",
									"No task selected. Please select task before deleting");
				} else {
					ModelProvider.INSTANCE.deleteTask(t);
					TaskView part = (TaskView) getViewSite().getPage()
							.findView(EditView.ID);
					part.refresh();
					clearFields();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		new Label(parent, SWT.NONE);
		
				Button btnClearFields = new Button(parent, SWT.NONE);
				btnClearFields.setToolTipText(BUNDLE.getString("EditView.btnClearFields.toolTipText")); //$NON-NLS-1$
				btnClearFields.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER,
						false, false, 1, 1));
				btnClearFields.setText(BUNDLE.getString("button.clear")); //$NON-NLS-1$
				btnClearFields.addSelectionListener(new SelectionListener() {

					@Override
					public void widgetSelected(SelectionEvent e) {
						clearFields();
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
					}
				});
		new Label(parent, SWT.NONE);

		getSite().getPage().addSelectionListener(listener);

	}

	private void clearFields() {
		idField.setText("");
		nameField.setText("");
		statusCombo.clearSelection();
		infoField.setText("");
	}

	private boolean isEverythingFilled() {
		if (nameField.getText().equals("") || statusCombo.getText().equals("")
				|| infoField.getText().equals("")) {
			return false;
		}
		return true;
	}

	@Override
	public void setFocus() {

	}

}
