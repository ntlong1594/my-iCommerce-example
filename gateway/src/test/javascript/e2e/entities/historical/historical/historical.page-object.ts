import { element, by, ElementFinder } from 'protractor';

export class HistoricalComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-userActivitiesHistorical div table .btn-danger'));
  title = element.all(by.css('jhi-userActivitiesHistorical div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class HistoricalUpdatePage {
  pageTitle = element(by.id('jhi-userActivitiesHistorical-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  userIdInput = element(by.id('field_userId'));
  correlationIdInput = element(by.id('field_correlationId'));
  actionIdInput = element(by.id('field_actionId'));
  actionDescriptionInput = element(by.id('field_actionDescription'));
  actionDateInput = element(by.id('field_actionDate'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setUserIdInput(userId: string): Promise<void> {
    await this.userIdInput.sendKeys(userId);
  }

  async getUserIdInput(): Promise<string> {
    return await this.userIdInput.getAttribute('value');
  }

  async setCorrelationIdInput(correlationId: string): Promise<void> {
    await this.correlationIdInput.sendKeys(correlationId);
  }

  async getCorrelationIdInput(): Promise<string> {
    return await this.correlationIdInput.getAttribute('value');
  }

  async setActionIdInput(actionId: string): Promise<void> {
    await this.actionIdInput.sendKeys(actionId);
  }

  async getActionIdInput(): Promise<string> {
    return await this.actionIdInput.getAttribute('value');
  }

  async setActionDescriptionInput(actionDescription: string): Promise<void> {
    await this.actionDescriptionInput.sendKeys(actionDescription);
  }

  async getActionDescriptionInput(): Promise<string> {
    return await this.actionDescriptionInput.getAttribute('value');
  }

  async setActionDateInput(actionDate: string): Promise<void> {
    await this.actionDateInput.sendKeys(actionDate);
  }

  async getActionDateInput(): Promise<string> {
    return await this.actionDateInput.getAttribute('value');
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class HistoricalDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-userActivitiesHistorical-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-userActivitiesHistorical'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
