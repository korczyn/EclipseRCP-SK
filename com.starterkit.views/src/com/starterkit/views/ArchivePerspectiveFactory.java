package com.starterkit.views;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class ArchivePerspectiveFactory implements IPerspectiveFactory {

	@Override
	public void createInitialLayout(IPageLayout layout) {
		// TODO Auto-generated method stub
		layout.setEditorAreaVisible(false);

	}

}
