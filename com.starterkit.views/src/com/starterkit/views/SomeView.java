package com.starterkit.views;

import javax.swing.text.View;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;

import com.starterkit.views.model.ModelProvider;
import com.starterkit.views.model.Task;

public class SomeView extends ViewPart {
	
	public static final String ID = "com.starterkit.views.TaskView";
	private Task t;
	private Text idField;
	private Text nameField;
	private Text statusField;
	private Text dateField;
	private Text infoField;
	
	ISelectionListener listener = new ISelectionListener() {
		
		@Override
		public void selectionChanged(IWorkbenchPart part, ISelection selection) {
			IStructuredSelection ss = (IStructuredSelection) selection;
			t = (Task) ss.getFirstElement();
			if(t != null){
				idField.setText(t.getId().toString());
				nameField.setText(t.getName());
				statusField.setText(t.getStatus());
				dateField.setText(t.getDate());
				infoField.setText(t.getInfo());
			}
		}
	};
     public void dispose() {
        getSite().getPage().removeSelectionListener(listener);
     }
	
	
	public SomeView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(3, false));
		
		Label lblNewLabel_2 = new Label(parent, SWT.NONE);
		lblNewLabel_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_2.setText("ID");
		
		idField = new Text(parent, SWT.BORDER | SWT.READ_ONLY);
		idField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		Label lblNewLabel = new Label(parent, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("Name");
		
		nameField = new Text(parent, SWT.BORDER);
		nameField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		Label lblDueDate = new Label(parent, SWT.NONE);
		lblDueDate.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDueDate.setText("Due date");
		
		dateField = new Text(parent, SWT.BORDER);
		dateField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		Label lblNewLabel_1 = new Label(parent, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setText("Status");
		
		statusField = new Text(parent, SWT.BORDER);
		statusField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		Label lblInfo = new Label(parent, SWT.NONE);
		lblInfo.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblInfo.setText("Info");
		
		infoField = new Text(parent, SWT.BORDER);
		infoField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		Button btnDeleteTask = new Button(parent, SWT.NONE);
		btnDeleteTask.setText("Delete task");
		btnDeleteTask.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(idField.getText().equals("")){
					MessageDialog.openError(null, "Error", "No task selected. Please select task before deleting");
				} else {
					ModelProvider.INSTANCE.deleteTask(t);
					TaskView part = (TaskView) getViewSite().getPage()
				              .findView(SomeView.ID);
					part.refresh();
					clearFields();
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
		Button btnSaveChanges = new Button(parent, SWT.NONE);
		btnSaveChanges.setText("Save changes");
		
		btnSaveChanges.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(isEverythingFilled()){
					Task t = new Task(Long.valueOf(idField.getText()), nameField.getText(), statusField.getText(), dateField.getText(), infoField.getText());
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
		btnAddTask.setText("Add task");
		
		
		btnAddTask.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(isEverythingFilled()){
					Task task = new Task(null, nameField.getText(), statusField.getText(), dateField.getText(), infoField.getText());
					ModelProvider.INSTANCE.addTask(task);
					TaskView part = (TaskView) getViewSite().getPage()
				              .findView(SomeView.ID);
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
		
		Button btnClearFields = new Button(parent, SWT.NONE);
		btnClearFields.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 3, 1));
		btnClearFields.setText("Clear fields");
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
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		
		getSite().getPage().addSelectionListener(listener);
		
		
	}
	
	private void clearFields(){
		idField.setText("");
		nameField.setText("");
		statusField.setText("");
		dateField.setText("");
		infoField.setText("");
	}
	
	private boolean isEverythingFilled(){
		if(nameField.getText().equals("") || dateField.getText().equals("") || statusField.getText().equals("") || infoField.getText().equals("")){
			return false;
		}
		return true;
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
