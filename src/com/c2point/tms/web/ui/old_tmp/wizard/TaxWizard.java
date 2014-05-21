package com.c2point.tms.web.ui.old_tmp.wizard;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.c2point.tms.web.ui.wizard.ButtonBar.ButtonType;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class TaxWizard extends VerticalLayout implements WizardListener{

	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager.getLogger( TaxWizard.class.getName());

	private WizardModel	wizardModel;

	private TaxProgressBar progressBar;
	private Component pageHolder;
	private ButtonBar buttonBar;
	
	
	public TaxWizard() {
		super();
		
		wizardModel = new WizardModel();
		wizardModel.addWizardListener( this );
		
		initUI();

		
	}
	
	protected void initUI() {

		
		
		setMargin( true );
		setSpacing( true );
		setSizeFull();
		setImmediate( true );
		
		createProgressBar();
		pageHolder = new Label();
		createButtonBar();

		Label caption = new Label( "<h1>Tax Report Creation Wizard</h1>" );
		caption.setContentMode( ContentMode.HTML );
		
		addComponent( caption );
		addComponent( new Label( "" ));
		
		
		addComponent( progressBar );
		addComponent( pageHolder );
		addComponent( buttonBar );

		setExpandRatio( pageHolder, 1.0f );
		
	}

	private void createProgressBar() {
		
		progressBar = new TaxProgressBar();
		
		progressBar.setWidth( "100%" );
		
		wizardModel.addWizardListener( progressBar );
		
	}
	private void createButtonBar() {

		buttonBar = new ButtonBar();
		
		wizardModel.addWizardListener( buttonBar );
		
		buttonBar.addClickListener( ButtonType.Cancel, wizardModel.getCancelListener());
		buttonBar.addClickListener( ButtonType.Finish, wizardModel.getFinishListener());
		buttonBar.addClickListener( ButtonType.Next, wizardModel.getNextListener());
		buttonBar.addClickListener( ButtonType.Back, wizardModel.getBackListener());
		
	}
	
	public void addPage( TaxWizardPage page ) {
		
		wizardModel.add( page );
	}

	@Override
	public void newPageAdded(WizardModel model, int index) {
		
		
	}

	@Override
	public void pageActivated( WizardModel model, int index ) {

		if ( logger.isDebugEnabled())  logger.debug( "PageActive event received. Shall show '" + model.getPage( index ).getCaption() + "' WizardPage!" );
		
		int compIndex = this.getComponentIndex( pageHolder );
		
		if ( compIndex >= 0 ) {
			
			this.removeComponent( pageHolder );
			
			pageHolder = model.getPage( index );
			
			if ( pageHolder != null ) {
				this.addComponent( pageHolder, compIndex );
				this.setExpandRatio( pageHolder, 1.0f );
			} else {
				logger.error( "Did not find page with index '" + index + "' in Wizard!!!" );
			}
			
		} else {
			logger.error( "Did not find pageHolder in Wizard!" );
		}

		
	}

	@Override
	public void pageDeactivated(WizardModel model, int index) {
		
	}

	
}
