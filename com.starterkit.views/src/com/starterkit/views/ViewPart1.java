package com.starterkit.views;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;

import com.starterkit.views.model.Person;

public class ViewPart1 extends ViewPart {
	private Text text;
	private Text text_1;

	public ViewPart1() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(2, false));
		
		Label lblName = new Label(parent, SWT.NONE);
		lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblName.setText("Name");
		
		text = new Text(parent, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(parent, SWT.NONE);
		
		text_1 = new Text(parent, SWT.BORDER);
		text_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		text_1.setEnabled(false);
		new Label(parent, SWT.NONE);
		
		Button btnJustDoIt = new Button(parent, SWT.NONE);
		btnJustDoIt.setText("JUST DO IT");
		new Label(parent, SWT.NONE);
		
		Button btnFail = new Button(parent, SWT.NONE);
		btnFail.setText("FAIL");
		btnJustDoIt.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				String name = text.getText();
				text_1.setText(name);
				
				MessageDialog.open(PROP_TITLE, null, "DOBRZE", "You have failed successfully", PROP_TITLE);
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		btnFail.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Person p = new Person();
		
		
		DataBindingContext ctx = new DataBindingContext();
		IObservableValue target = WidgetProperties.text(SWT.Modify).observe(text);
		IObservableValue model = BeanProperties.value(Person.class, "firstName").observe(p);
		
		ctx.bindValue(target, model);
		
		IObservableValue target2 = WidgetProperties.text(SWT.Modify).observe(text_1);
		ctx.bindValue(target2, model);
		
		
	}
	
	

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
