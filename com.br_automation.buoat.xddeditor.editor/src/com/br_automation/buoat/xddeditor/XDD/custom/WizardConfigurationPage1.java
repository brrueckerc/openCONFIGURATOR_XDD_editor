/**
 * @since 19.3.2013
 * @author Joris L�ckenga, Bernecker + Rainer Industrie Elektronik Ges.m.b.H.
 */

package com.br_automation.buoat.xddeditor.XDD.custom;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.br_automation.buoat.xddeditor.XDD.ProductNameType;
import com.br_automation.buoat.xddeditor.XDD.TProductID;
import com.br_automation.buoat.xddeditor.XDD.TVendorID;
import com.br_automation.buoat.xddeditor.XDD.TVersion;
import com.br_automation.buoat.xddeditor.XDD.VendorNameType;
import com.br_automation.buoat.xddeditor.XDD.VersionTypeType;
import com.br_automation.buoat.xddeditor.XDD.XDDFactory;
import com.br_automation.buoat.xddeditor.XDD.validation.NameVerifyListener;
import com.br_automation.buoat.xddeditor.XDD.wizards.DataTypeRange;

/**
 * @brief Advanced configurationPage for a new XDD Model.
 *
 *        Provides different controls to set options and data in the
 *        "AdvancedWizard"-page.
 *
 * @author Joris L�ckenga
 */
public class WizardConfigurationPage1 extends WizardPage {

    private Button btnCnMultiplexFeature;
    private Button btnMultipleASnd;
    private Button btnNWLIPSupport;
    private Button btnResponseChaining;
    private CustomXDDWizard mainwizard;

    // Metadata
    private Text txtCreator;
    private Text txtDeviceName;
    private Text txtFileVersion;
    private Text txtFirmwareVersionNr;
    private Text txtHardwareVersNr;
    private Text txtNMTBootTimeNotActive;
    // Feature-Settings
    private Text txtNMTCNSoC2PReq;
    private Text txtNMTCycleTimeMax;
    private Text txtNMTCycleTimeMin;
    private Text txtNMTErrorEntries;
    private Text txtProductID;
    private Text txtProductName;
    private Text txtSoftwareVersNr;
    private Text txtVendorID;
    // Product/Vendor variables
    private Text txtVendorName;

    private static final String INVALID_VENDOR_ID = "Invalid vendor ID for the device.";
    private static final String INVALID_VENDOR_ID_NULL_ERROR = "Vendor ID cannot be empty";
    private static final String INVALID_PRODUCT_ID_NULL_ERROR = "Product ID cannot be empty";
    private static final String INVALID_PRODUCT_NAME_EMPTY_ERROR = "Product name cannot be empty.";
    private static final String INVALID_PRODUCT_ID_VALUE = "Invalid product ID for the device.";
    private static final String INVALID_VENDOR_NAME_EMPTY_ERROR = "Vendor name cannot be empty.";

    public static final String INVALID_PREQ_TIME_VALUE_EMPTY_ERROR = "PReq time cannot be empty.";
    public static final String INVALID_NETWORK_EMPTY_ERROR_ENTRIES = "Network error entries cannot be empty.";
    public static final String INVALID_MAX_CYCLE_TIME = "Maximum cycle time value invalid.";
    public static final String INVALID_MIN_CYCLE_TIME = "Invalid minimum cycle time value.";
    public static final String EMPTY_CYCLE_TIME_ERROR_MESSAGE = "Cycle time cannot be empty.";
    public static final String EMPTY_MIN_CYCLE_TIME_ERROR_MESSAGE = "Minimum cycle time cannot be empty.";
    public static final String EMPTY_NETWORK_BOOT_TIME_ERROR_MESSAGE = "Network boot time cannot be empty.";
    public static final String INVALID_NETWORK_BOOT_TIME = "Invalid network boot time value.";

    private static final String INVALID_PREQ_TIME_SPACE_ERROR = "PReq time cannot start with spaces.";
    private static final String INVALID_NETWORK_BOOT_TIME_SPACE_ERROR = "Network boot time cannot start with spaces.";
    private static final String INVALID_NETWORK_ERROR_ENTRIES_SPACE_ERROR = "Network error entries cannot start with spaces.";

    /**
     * @param pageID
     *            ID of the page.
     * @param wizard
     *            the parent-wizard.
     */
    public WizardConfigurationPage1(String pageID, CustomXDDWizard wizard) {
        super(pageID);
        this.setTitle(Messages.wizardConfigurationPage1_adv_conf_page_title);
        this.setDescription(Messages.wizardConfigurationPage1_type_in_data_new_model_subheadline);
        this.mainwizard = wizard;
        this.mainwizard.getWizardTemplatePage().setPageComplete(true);
        this.setPageComplete(true);
    }

    /**
     * @see WizardPage#createControl(Composite)
     */
    @Override
    public void createControl(Composite parent) {
        Composite container = new Composite(parent, SWT.NONE);
        this.setControl(container);
        SimpleDateFormat creationDate = new SimpleDateFormat("yyyy-MM-dd"); //$NON-NLS-1$
        String creationDateStr = creationDate.format(new Date());

        SimpleDateFormat creationTime = new SimpleDateFormat("HH:mm:ssZ"); //$NON-NLS-1$
        String creationTimeStr = creationTime.format(new Date());
        creationTimeStr = creationTimeStr.substring(0, 11) + ":00"; //$NON-NLS-1$

        Group grpMetadata = new Group(container, SWT.NONE);
        grpMetadata.setText(Messages.wizardConfigurationPage1_metadata_lbl);
        grpMetadata.setBounds(297, 10, 242, 144);

        Label lblDeviceName = new Label(grpMetadata, SWT.NONE);
        lblDeviceName.setBounds(10, 91, 82, 15);
        lblDeviceName.setText(Messages.wizardConfigurationPage1_device_name_lbl);

        this.txtDeviceName = new Text(grpMetadata, SWT.BORDER);
        this.txtDeviceName.setBounds(98, 88, 134, 21);
        this.txtDeviceName.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (WizardConfigurationPage1.this.txtDeviceName.getText().isEmpty())
                    WizardConfigurationPage1.this.setPageComplete(false);
                else
                    WizardConfigurationPage1.this.setPageComplete(true);
            }
        });
        this.txtDeviceName.setText("New_Device"); //$NON-NLS-1$

        this.txtCreator = new Text(grpMetadata, SWT.BORDER);
        this.txtCreator.setBounds(98, 64, 134, 21);
        this.txtCreator.setText(System.getProperty("user.name")); //$NON-NLS-1$

        Label lblCreator = new Label(grpMetadata, SWT.NONE);
        lblCreator.setBounds(10, 67, 55, 15);
        lblCreator.setText(Messages.wizardConfigurationPage1_creator_lbl);

        Label lblCreationTime = new Label(grpMetadata, SWT.NONE);
        lblCreationTime.setBounds(10, 43, 82, 15);
        lblCreationTime.setText(Messages.wizardConfigurationPage1_creation_time_lbl);

        Label lblContentCreationTime = new Label(grpMetadata, SWT.NONE);
        lblContentCreationTime.setBounds(98, 43, 134, 15);
        lblContentCreationTime.setText(creationTimeStr);

        Label lblCreationDate = new Label(grpMetadata, SWT.NONE);
        lblCreationDate.setBounds(10, 22, 82, 15);
        lblCreationDate.setText(Messages.wizardConfigurationPage1_creation_date_lbl);

        Label lblContentCreationDate = new Label(grpMetadata, SWT.NONE);
        lblContentCreationDate.setBounds(98, 22, 134, 15);
        lblContentCreationDate.setText(creationDateStr);

        Label lblFileVersion = new Label(grpMetadata, SWT.NONE);
        lblFileVersion.setBounds(10, 115, 82, 15);
        lblFileVersion.setText(Messages.wizardConfigurationPage1_file_version_lbl);

        this.txtFileVersion = new Text(grpMetadata, SWT.BORDER);
        this.txtFileVersion.setText("1.00"); // NOPMD by //$NON-NLS-1$
                                                // lueckengaj on 29.03.13 11:31
        this.txtFileVersion.setBounds(98, 112, 134, 21);

        Group grpAda = new Group(container, SWT.NONE);
        grpAda.setText(Messages.wizardConfigurationPage1_vendor_prod_info_lbl);
        grpAda.setBounds(10, 10, 281, 199);

        Label lblVendorName = new Label(grpAda, SWT.NONE);
        lblVendorName.setBounds(10, 22, 91, 15);
        lblVendorName.setText(Messages.wizardConfigurationPage1_vendor_name_lbl);

        Label lblVendorId = new Label(grpAda, SWT.NONE);
        lblVendorId.setText(Messages.wizardConfigurationPage1_vendor_id_lbl);
        lblVendorId.setBounds(10, 46, 73, 15);

        Label lblProductName = new Label(grpAda, SWT.NONE);
        lblProductName.setText(Messages.wizardConfigurationPage1_product_name_lbl);
        lblProductName.setBounds(10, 70, 91, 15);

        Label lblHardwareVersionNr = new Label(grpAda, SWT.NONE);
        lblHardwareVersionNr.setText(Messages.wizardConfigurationPage1_hardware_vers_nr_lbl);
        lblHardwareVersionNr.setBounds(10, 118, 118, 15);

        Label lblSoftwareVersionNr = new Label(grpAda, SWT.NONE);
        lblSoftwareVersionNr.setText(Messages.wizardConfigurationPage1_software_version_nr_lbl);
        lblSoftwareVersionNr.setBounds(10, 142, 118, 15);

        this.txtVendorName = new Text(grpAda, SWT.BORDER);
        this.txtVendorName.setText("Sample_Vendor"); //$NON-NLS-1$
        this.txtVendorName.setBounds(128, 19, 143, 21);

        this.txtVendorID = new Text(grpAda, SWT.BORDER);
        this.txtVendorID.setText("0x00000000"); //$NON-NLS-1$
        this.txtVendorID.setBounds(128, 43, 143, 21);


        this.txtProductName = new Text(grpAda, SWT.BORDER);
        this.txtProductName.setText("Sample_Product_Name"); //$NON-NLS-1$
        this.txtProductName.setBounds(128, 67, 143, 21);

        this.txtHardwareVersNr = new Text(grpAda, SWT.BORDER);
        this.txtHardwareVersNr.setText("1.00"); //$NON-NLS-1$
        this.txtHardwareVersNr.setBounds(128, 115, 143, 21);

        this.txtSoftwareVersNr = new Text(grpAda, SWT.BORDER);
        this.txtSoftwareVersNr.setText("1.00"); //$NON-NLS-1$
        this.txtSoftwareVersNr.setBounds(128, 139, 143, 21);

        Label lblProductID = new Label(grpAda, SWT.NONE);
        lblProductID.setText("Product ID:"); //$NON-NLS-1$
        lblProductID.setBounds(10, 94, 118, 15);

        this.txtProductID = new Text(grpAda, SWT.BORDER);
        this.txtProductID.setText("EPSG-001"); //$NON-NLS-1$
        this.txtProductID.setBounds(128, 91, 143, 21);

        Label lblFirmwareVersionNr = new Label(grpAda, SWT.NONE);
        lblFirmwareVersionNr.setText(Messages.wizardConfigurationPage1_firmversion_nr_lbl);
        lblFirmwareVersionNr.setBounds(10, 166, 118, 15);

        this.txtFirmwareVersionNr = new Text(grpAda, SWT.BORDER);
        this.txtFirmwareVersionNr.setText("1.00"); //$NON-NLS-1$
        this.txtFirmwareVersionNr.setBounds(128, 163, 143, 21);

        Group grpCnFeatures = new Group(container, SWT.NONE);
        grpCnFeatures.setText("Node Features"); //$NON-NLS-1$
        grpCnFeatures.setBounds(297, 225, 254, 113);

        this.btnCnMultiplexFeature = new Button(grpCnFeatures, SWT.CHECK);
        this.btnCnMultiplexFeature.setToolTipText(Messages.wizardConfigurationPage1_multiplex_feature_tooltip);
        this.btnCnMultiplexFeature.setBounds(10, 22, 222, 16);
        this.btnCnMultiplexFeature.setText("Multiplexed Communication"); //$NON-NLS-1$

        this.btnResponseChaining = new Button(grpCnFeatures, SWT.CHECK);
        this.btnResponseChaining.setToolTipText(Messages.wizardConfigurationPage1_Response_chaining_tooltip);
        this.btnResponseChaining.setBounds(10, 44, 234, 16);
        this.btnResponseChaining.setText("Poll Response Chaining Communication"); //$NON-NLS-1$

        Label lblSoc = new Label(grpCnFeatures, SWT.NONE);
        lblSoc.setToolTipText(Messages.wizardConfigurationPage1_Time_for_cn_to_process_Soc_tooltip);
        lblSoc.setText("Time for PReq (ns):"); //$NON-NLS-1$
        lblSoc.setBounds(10, 66, 124, 15);

        this.txtNMTCNSoC2PReq = new Text(grpCnFeatures, SWT.BORDER);
        this.txtNMTCNSoC2PReq.setText("25"); //$NON-NLS-1$
        this.txtNMTCNSoC2PReq.setToolTipText(Messages.wizardConfigurationPage1_time_for_CN_tro_process_SoC_tooltip);
        this.txtNMTCNSoC2PReq.setBounds(140, 63, 92, 21);

        Group grpGeneralFeatures = new Group(container, SWT.SHADOW_OUT);
        grpGeneralFeatures.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL)); //$NON-NLS-1$
        grpGeneralFeatures.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
        grpGeneralFeatures.setText(Messages.wizardConfigurationPage1_general_features);
        grpGeneralFeatures.setBounds(10, 225, 281, 156);

        Label lblNmtboottimenotactive = new Label(grpGeneralFeatures, SWT.NONE);
        lblNmtboottimenotactive.setBounds(10, 20, 163, 15);
        lblNmtboottimenotactive.setText("Network Boot Time (\u00B5s):"); //$NON-NLS-1$

        Label lblNewLabel1 = new Label(grpGeneralFeatures, SWT.NONE);
        lblNewLabel1.setBounds(10, 42, 163, 15);
        lblNewLabel1.setText("Maximum Cycle Time (\u00B5s):"); //$NON-NLS-1$

        Label lblNewLabel2 = new Label(grpGeneralFeatures, SWT.NONE);
        lblNewLabel2.setBounds(10, 64, 163, 15);
        lblNewLabel2.setText("Minimum Cycle Time (\u00B5s):"); //$NON-NLS-1$

        Label lblNewLabel3 = new Label(grpGeneralFeatures, SWT.NONE);
        lblNewLabel3.setToolTipText(Messages.wizardConfigurationPage1_nr_of_Errors_reported_to_MN);
        lblNewLabel3.setBounds(10, 86, 163, 15);
        lblNewLabel3.setText("Total Network Error Entries:"); //$NON-NLS-1$

        this.txtNMTBootTimeNotActive = new Text(grpGeneralFeatures, SWT.BORDER);
        this.txtNMTBootTimeNotActive.setText("8000000"); //$NON-NLS-1$
        this.txtNMTBootTimeNotActive.setBounds(179, 14, 92, 21);

        this.txtNMTCycleTimeMax = new Text(grpGeneralFeatures, SWT.BORDER);
        this.txtNMTCycleTimeMax.setText("1000000"); //$NON-NLS-1$
        this.txtNMTCycleTimeMax.setBounds(179, 36, 92, 21);

        this.txtNMTCycleTimeMin = new Text(grpGeneralFeatures, SWT.BORDER);
        this.txtNMTCycleTimeMin.setText("1000"); //$NON-NLS-1$
        this.txtNMTCycleTimeMin.setBounds(179, 58, 92, 21);

        this.txtNMTErrorEntries = new Text(grpGeneralFeatures, SWT.BORDER);
        this.txtNMTErrorEntries.setText("8"); //$NON-NLS-1$
        this.txtNMTErrorEntries.setBounds(179, 80, 92, 21);

        this.btnNWLIPSupport = new Button(grpGeneralFeatures, SWT.CHECK);
        this.btnNWLIPSupport.setBounds(10, 130, 146, 16);
        this.btnNWLIPSupport.setToolTipText(Messages.wizardConfigurationPage1_EnableDisableIPSupport); // $NON-NLS-1$
        this.btnNWLIPSupport.setText("Network IP Support");

        this.btnMultipleASnd = new Button(grpGeneralFeatures, SWT.CHECK);
        this.btnMultipleASnd.setToolTipText(Messages.wizardConfigurationPage1_btnMultiASnd_text);
        this.btnMultipleASnd.setText("IP Support");
        this.btnMultipleASnd.setBounds(10, 107, 146, 16);

        addListenersToControls();
    } // createControl

    // Metadata getters

    private ModifyListener timeForPreqModifyListener = new ModifyListener() {

        @Override
        public void modifyText(ModifyEvent e) {
            setErrorMessage(null);
            setPageComplete(true);
            String timeforPreqText = txtNMTCNSoC2PReq.getText();
            try {

                if (timeforPreqText == null) {
                    setErrorMessage(INVALID_PREQ_TIME_VALUE_EMPTY_ERROR);
                    setPageComplete(false);
                }

                if (timeforPreqText.length() <= 0) {
                    setErrorMessage(INVALID_PREQ_TIME_VALUE_EMPTY_ERROR);
                    setPageComplete(false);
                }

                // Space as first character is not allowed. ppc:tNonEmptyString
                if (timeforPreqText.contains(" ")) {
                    setErrorMessage(INVALID_PREQ_TIME_SPACE_ERROR);
                    setPageComplete(false);
                }

                Long value = Long.parseLong(timeforPreqText);
                if (value < DataTypeRange.Unsigned32_min || value > DataTypeRange.Unsigned32_max) {
                    setErrorMessage("Time for PReq value '" + timeforPreqText
                            + "' does not fit within the range (0 - 4,294,967,295) of data type 'Unsigned32'.");
                    setPageComplete(false);

                }
            } catch (Exception ex) {
                ex.printStackTrace();
                setPageComplete(false);
            }


        }
    };

    private ModifyListener totalnetworkEntriesModifyListener = new ModifyListener() {

        @Override
        public void modifyText(ModifyEvent e) {
            setErrorMessage(null);
            setPageComplete(true);
            String totalNetworkEntry = txtNMTErrorEntries.getText();
            try {

                if (totalNetworkEntry == null) {
                    setErrorMessage(INVALID_NETWORK_EMPTY_ERROR_ENTRIES);
                    setPageComplete(false);
                }

                if (totalNetworkEntry.length() <= 0) {
                    setErrorMessage(INVALID_NETWORK_EMPTY_ERROR_ENTRIES);
                    setPageComplete(false);
                }

                // Space as first character is not allowed. ppc:tNonEmptyString
                if (totalNetworkEntry.contains(" ")) {
                    setErrorMessage(INVALID_NETWORK_ERROR_ENTRIES_SPACE_ERROR);
                    setPageComplete(false);
                }

                Long value = Long.parseLong(totalNetworkEntry);
                if (value < DataTypeRange.Unsigned32_min || value > DataTypeRange.Unsigned32_max) {
                    setErrorMessage("Total network error entries '" + totalNetworkEntry
                            + "' does not fit within the range (0 - 4,294,967,295) of data type 'Unsigned32'.");
                    setPageComplete(false);

                }

            } catch (NumberFormatException ex) {
                setErrorMessage(INVALID_NETWORK_EMPTY_ERROR_ENTRIES);
                ex.printStackTrace();
                setPageComplete(false);
            }

        }
    };

    /**
     * Name verify listener
     */
    private NameVerifyListener nameVerifyListener = new NameVerifyListener();

    private void addListenersToControls() {

        txtNMTCycleTimeMin.addModifyListener(minimumCycleTimeModifyListener);
        txtNMTBootTimeNotActive.addModifyListener(networkBootTimeModifyListener);
        txtNMTCycleTimeMax.addModifyListener(maximumCycleTimeModifyListener);
        txtNMTErrorEntries.addModifyListener(totalnetworkEntriesModifyListener);
        txtNMTCycleTimeMin.addVerifyListener(nameVerifyListener);
        txtNMTBootTimeNotActive.addVerifyListener(nameVerifyListener);
        txtNMTCycleTimeMax.addVerifyListener(nameVerifyListener);
        txtNMTErrorEntries.addVerifyListener(nameVerifyListener);

        txtNMTCNSoC2PReq.addModifyListener(timeForPreqModifyListener);
        txtNMTCNSoC2PReq.addVerifyListener(nameVerifyListener);

        txtVendorID.addModifyListener(vendorIdModifyListener);
        txtVendorID.addVerifyListener(nameVerifyListener);

        txtVendorName.addModifyListener(vendorNameModifyListener);
        txtProductID.addVerifyListener(nameVerifyListener);

        txtProductID.addModifyListener(productIdModifyListener);
        txtProductName.addModifyListener(productNameModifyListener);

    }

    private ModifyListener vendorIdModifyListener = new ModifyListener() {

        @Override
        public void modifyText(ModifyEvent e) {

            setErrorMessage(null);
            setPageComplete(true);

            String vendorId = txtVendorID.getText();
            try {

                if (vendorId == null) {
                    setErrorMessage(INVALID_VENDOR_ID_NULL_ERROR);
                    setPageComplete(false);
                }

                if (vendorId.length() <= 0) {
                    setErrorMessage(INVALID_VENDOR_ID_NULL_ERROR);
                    setPageComplete(false);
                }

                // Space as first character is not allowed. ppc:tNonEmptyString
                if (vendorId.contains(" ")) {
                    setErrorMessage(INVALID_VENDOR_ID);
                    setPageComplete(false);
                }

                if (vendorId.contains("0x")) {
                    String val = vendorId.substring(2);

                    if (vendorId.length() > 10) {
                        setErrorMessage(INVALID_VENDOR_ID);
                        setPageComplete(false);
                    }
                    if (!val.isEmpty()) {
                        long vendorIdVal = Long.parseLong(val, 16);
                        if (vendorIdVal < DataTypeRange.Unsigned32_min || vendorIdVal > DataTypeRange.Unsigned32_max) {
                            setErrorMessage("Vendor ID value '" + vendorIdVal
                                    + "' does not fit within the range (0 - 4,294,967,295) of data type 'Unsigned32'.");
                            setPageComplete(false);

                        }
                    }
                } else {
                    setErrorMessage(INVALID_VENDOR_ID);

                    setPageComplete(false);
                }

            } catch (NumberFormatException ex) {
                setErrorMessage(INVALID_VENDOR_ID);
                ex.printStackTrace();
                setPageComplete(false);
            }



        }
    };

    public XMLGregorianCalendar getCreationDateXML() {
        return XDDUtilities.getXMLDate();
    }

    public XMLGregorianCalendar getCreationTimeXML() {
        return XDDUtilities.getXMLTime();
    }

    public String getCreatorString() {
        return this.txtCreator.getText().trim();
    }

    public String getDeviceNameString() {
        return this.txtDeviceName.getText().trim();
    }

    public String getFileNameString() {
        return this.mainwizard.getNewFileCreationPage().getFileName();
    }

    public String getFileVersionString() {
        return this.txtFileVersion.getText().trim();
    }

    private ModifyListener maximumCycleTimeModifyListener = new ModifyListener() {

        @Override
        public void modifyText(ModifyEvent e) {
            setErrorMessage(null);
            setPageComplete(true);
            String maximumCycleTime = txtNMTCycleTimeMax.getText();
            String minimumCycleTime = txtNMTCycleTimeMin.getText();
            try {

                if (maximumCycleTime == null) {
                    setErrorMessage(EMPTY_CYCLE_TIME_ERROR_MESSAGE);
                    setPageComplete(false);
                }

                if (maximumCycleTime.length() <= 0) {
                    setErrorMessage(EMPTY_CYCLE_TIME_ERROR_MESSAGE);
                    setPageComplete(false);
                }

                // Space as first character is not allowed. ppc:tNonEmptyString
                if (maximumCycleTime.contains(" ")) {
                    setErrorMessage(INVALID_MAX_CYCLE_TIME);
                    setPageComplete(false);
                }

                Long value = Long.parseLong(maximumCycleTime);
                if (value < DataTypeRange.Unsigned32_min || value > DataTypeRange.Unsigned32_max) {
                    setErrorMessage("Maximum cycle time value '" + maximumCycleTime
                            + "' does not fit within the range (0 - 4,294,967,295) of data type 'Unsigned32'.");
                    setPageComplete(false);

                }

                if (!minimumCycleTime.isEmpty()) {
                    Long minValue = Long.parseLong(minimumCycleTime);
                    if (value < minValue) {
                        setErrorMessage("Maximum cycle time value '" + value
                                + "' cannot be lesser than minimum cycle time value '" + minimumCycleTime + "'.");
                    }
                }

            } catch (NumberFormatException ex) {
                setErrorMessage(INVALID_MAX_CYCLE_TIME);
                ex.printStackTrace();
                setPageComplete(false);
            }
            setPageComplete(false);

        }

    };

    private ModifyListener minimumCycleTimeModifyListener = new ModifyListener() {

        @Override
        public void modifyText(ModifyEvent e) {
            setErrorMessage(null);
            setPageComplete(true);
            String minimumCycleTime = txtNMTCycleTimeMin.getText();
            String maximumCycleTime = txtNMTCycleTimeMax.getText();
            try {

                if (minimumCycleTime == null) {
                    setErrorMessage(EMPTY_MIN_CYCLE_TIME_ERROR_MESSAGE);
                    setPageComplete(false);
                }

                if (minimumCycleTime.length() <= 0) {
                    setErrorMessage(EMPTY_MIN_CYCLE_TIME_ERROR_MESSAGE);
                    setPageComplete(false);
                }

                // Space as first character is not allowed. ppc:tNonEmptyString
                if (minimumCycleTime.contains(" ")) {
                    setErrorMessage(INVALID_MIN_CYCLE_TIME);
                    setPageComplete(false);
                }

                Long value = Long.parseLong(minimumCycleTime);
                if (value < DataTypeRange.Unsigned32_min || value > DataTypeRange.Unsigned32_max) {
                    setErrorMessage("Minimum cycle time value '" + minimumCycleTime
                            + "' does not fit within the range (0 - 4,294,967,295) of data type 'Unsigned32'.");
                    setPageComplete(false);

                }

                if (!maximumCycleTime.isEmpty()) {
                    Long maxValue = Long.parseLong(maximumCycleTime);
                    if (value > maxValue) {
                        setErrorMessage("Minimum cycle time value '" + value
                                + "' cannot be greater than maximum cycle time value '" + maximumCycleTime + "'.");
                    }
                }

            } catch (NumberFormatException ex) {
                setErrorMessage(INVALID_MIN_CYCLE_TIME);
                ex.printStackTrace();
                setPageComplete(false);
            }

        }
    };

    /**
     * Checks for error in the page
     *
     * @return page complete status.
     */
    @Override
    public boolean isPageComplete() {

        boolean pageComplete = (super.isPageComplete());
System.err.println("Page complete.."+pageComplete);
        return pageComplete;
    }

    private ModifyListener productNameModifyListener = new ModifyListener() {

        @Override
        public void modifyText(ModifyEvent e) {

            setErrorMessage(null);
            setPageComplete(true);

            String productName = txtProductName.getText();
            try {

                if (productName.isEmpty()) {
                    setErrorMessage(INVALID_PRODUCT_NAME_EMPTY_ERROR);
                    setPageComplete(false);
                }

                if (productName.length() <= 0) {
                    setErrorMessage(INVALID_PRODUCT_NAME_EMPTY_ERROR);
                    setPageComplete(false);
                }

            } catch (NumberFormatException ex) {
                setErrorMessage(INVALID_PRODUCT_NAME_EMPTY_ERROR);
                ex.printStackTrace();
                setPageComplete(false);
            }



        }
    };

    private ModifyListener productIdModifyListener = new ModifyListener() {

        @Override
        public void modifyText(ModifyEvent e) {

            setErrorMessage(null);
            setPageComplete(true);
            String productId = txtProductID.getText();
            try {

                if (productId.isEmpty()) {
                    setErrorMessage(INVALID_PRODUCT_ID_NULL_ERROR);
                    setPageComplete(false);
                }

                if (productId.length() <= 0) {
                    setErrorMessage(INVALID_PRODUCT_ID_NULL_ERROR);
                    setPageComplete(false);
                }

                // Space as first character is not allowed. ppc:tNonEmptyString
                if (productId.contains(" ")) {
                    setErrorMessage(INVALID_PRODUCT_ID_VALUE);
                    setPageComplete(false);
                }

                if (productId.contains("0x")) {
                    String val = productId.substring(2);
                    if (productId.length() > 10) {
                        setErrorMessage(INVALID_PRODUCT_ID_VALUE);
                        setPageComplete(false);
                    }
                    if (!val.isEmpty()) {
                        long productIdVal = Long.parseLong(val, 16);
                        if (productIdVal < DataTypeRange.Unsigned32_min
                                || productIdVal > DataTypeRange.Unsigned32_max) {
                            setErrorMessage("Product ID value '" + productIdVal
                                    + "' does not fit within the range (0 - 4,294,967,295) of data type 'Unsigned32'.");
                            setPageComplete(false);

                        }
                    }
                } else {
                    setErrorMessage(INVALID_PRODUCT_ID_VALUE);

                    setPageComplete(false);
                }

            } catch (NumberFormatException ex) {
                setErrorMessage(INVALID_PRODUCT_ID_VALUE);
                ex.printStackTrace();
                setPageComplete(false);
            }

        }
    };

    private ModifyListener vendorNameModifyListener = new ModifyListener() {

        @Override
        public void modifyText(ModifyEvent e) {

            setErrorMessage(null);
            setPageComplete(true);
            String vendorName = txtVendorName.getText();
            try {

                if (vendorName.isEmpty()) {
                    setErrorMessage(INVALID_VENDOR_NAME_EMPTY_ERROR);
                    setPageComplete(false);
                }

                if (vendorName.length() <= 0) {
                    setErrorMessage(INVALID_VENDOR_NAME_EMPTY_ERROR);
                    setPageComplete(false);
                }

            } catch (NumberFormatException ex) {
                setErrorMessage(INVALID_VENDOR_NAME_EMPTY_ERROR);
                ex.printStackTrace();
                setPageComplete(false);
            }

        }
    };

    private ModifyListener networkBootTimeModifyListener = new ModifyListener() {

        @Override
        public void modifyText(ModifyEvent e) {
            setErrorMessage(null);
            setPageComplete(true);
            String networkBootTime = txtNMTBootTimeNotActive.getText();
            try {

                if (networkBootTime == null) {
                    setErrorMessage(EMPTY_NETWORK_BOOT_TIME_ERROR_MESSAGE);
                    setPageComplete(false);
                }

                if (networkBootTime.length() <= 0) {
                    setErrorMessage(EMPTY_NETWORK_BOOT_TIME_ERROR_MESSAGE);
                    setPageComplete(false);
                }

                // Space as first character is not allowed. ppc:tNonEmptyString
                if (networkBootTime.contains(" ")) {
                    setErrorMessage(INVALID_NETWORK_BOOT_TIME_SPACE_ERROR);
                    setPageComplete(false);
                }

                Long value = Long.parseLong(networkBootTime);
                if (value < DataTypeRange.Unsigned32_min || value > DataTypeRange.Unsigned32_max) {
                    setErrorMessage("Network boot time value '" + networkBootTime
                            + "' does not fit within the range (0 - 4,294,967,295) of data type 'Unsigned32'.");
                    setPageComplete(false);

                }

            } catch (NumberFormatException ex) {
                setErrorMessage(INVALID_NETWORK_BOOT_TIME);
                ex.printStackTrace();
                setPageComplete(false);
            }



        }
    };

    public String getFirmwareversString() {
        return this.txtFirmwareVersionNr.getText().trim();
    }

    public String getHardwareversString() {
        return this.txtHardwareVersNr.getText().trim();
    }

    /**
     * @return The NMTBootTimeNotActive time setted in the wizard as
     *         integer-value.
     */
    public Integer getNMTBootTimeNotActive() {
        if (!this.txtNMTBootTimeNotActive.getText().isEmpty())
            return Integer.parseInt(this.txtNMTBootTimeNotActive.getText());
        return null;
    }

    /**
     * @return The NMTCNSoC2PReq time setted in the wizard as integer-value.
     */
    public Integer getNMTCNSoC2PReq() {
        if (!this.txtNMTCNSoC2PReq.getText().isEmpty())
            return Integer.parseInt(this.txtNMTCNSoC2PReq.getText());
        return null;
    }

    /**
     * @return The NMTCycleTimeMax setted in the wizard as integer-value.
     */
    public Integer getNMTCycleTimeMax() {
        if (!this.txtNMTCycleTimeMax.getText().isEmpty())
            return Integer.parseInt(this.txtNMTCycleTimeMax.getText());
        return null;
    }

    /**
     * @return The CycleTimeMin setted in the wizard as integer-value.
     */
    public Integer getNMTCycleTimeMin() {
        if (!this.txtNMTCycleTimeMin.getText().isEmpty())
            return Integer.parseInt(this.txtNMTCycleTimeMin.getText());
        return null;
    }

    /**
     * @return The ErrorEntries setted in the wizard as integer-value.
     */
    public Integer getNMTErrorEntries() {
        if (!this.txtNMTErrorEntries.getText().isEmpty())
            return Integer.parseInt(this.txtNMTErrorEntries.getText());
        return null;
    }

    public String getProductIDString() {
        return this.txtProductID.getText();
    }

    public String getProductNameString() {
        return this.txtProductName.getText();
    }

    public String getSoftwareversString() {
        return this.txtSoftwareVersNr.getText();
    }

    public String getVendorIDString() {
        return this.txtVendorID.getText();
    }

    public String getVendorNameString() {
        return this.txtVendorName.getText();
    }

    public boolean isCnMultiplexFeature() {
        return this.btnCnMultiplexFeature.getSelection();
    }

    public boolean isMultipleASnd() {
        return this.btnMultipleASnd.getSelection();
    }

    public boolean isNWLIPSupport() {
        return this.btnNWLIPSupport.getSelection();
    }

    public boolean isResponseChaining() {
        return this.btnResponseChaining.getSelection();
    }
} // WizardConfigurationPage1
