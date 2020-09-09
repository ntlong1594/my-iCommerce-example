import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { HistoricalComponentsPage, HistoricalDeleteDialog, HistoricalUpdatePage } from './userActivitiesHistorical.page-object';

const expect = chai.expect;

describe('Historical e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let historicalComponentsPage: HistoricalComponentsPage;
  let historicalUpdatePage: HistoricalUpdatePage;
  let historicalDeleteDialog: HistoricalDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Historicals', async () => {
    await navBarPage.goToEntity('userActivitiesHistorical');
    historicalComponentsPage = new HistoricalComponentsPage();
    await browser.wait(ec.visibilityOf(historicalComponentsPage.title), 5000);
    expect(await historicalComponentsPage.getTitle()).to.eq('gatewayApp.historicalHistorical.home.title');
    await browser.wait(ec.or(ec.visibilityOf(historicalComponentsPage.entities), ec.visibilityOf(historicalComponentsPage.noResult)), 1000);
  });

  it('should load create Historical page', async () => {
    await historicalComponentsPage.clickOnCreateButton();
    historicalUpdatePage = new HistoricalUpdatePage();
    expect(await historicalUpdatePage.getPageTitle()).to.eq('gatewayApp.historicalHistorical.home.createOrEditLabel');
    await historicalUpdatePage.cancel();
  });

  it('should create and save Historicals', async () => {
    const nbButtonsBeforeCreate = await historicalComponentsPage.countDeleteButtons();

    await historicalComponentsPage.clickOnCreateButton();

    await promise.all([
      historicalUpdatePage.setUserIdInput('userId'),
      historicalUpdatePage.setCorrelationIdInput('correlationId'),
      historicalUpdatePage.setActionIdInput('actionId'),
      historicalUpdatePage.setActionDescriptionInput('actionDescription'),
      historicalUpdatePage.setActionDateInput('2000-12-31'),
    ]);

    expect(await historicalUpdatePage.getUserIdInput()).to.eq('userId', 'Expected UserId value to be equals to userId');
    expect(await historicalUpdatePage.getCorrelationIdInput()).to.eq(
      'correlationId',
      'Expected CorrelationId value to be equals to correlationId'
    );
    expect(await historicalUpdatePage.getActionIdInput()).to.eq('actionId', 'Expected ActionId value to be equals to actionId');
    expect(await historicalUpdatePage.getActionDescriptionInput()).to.eq(
      'actionDescription',
      'Expected ActionDescription value to be equals to actionDescription'
    );
    expect(await historicalUpdatePage.getActionDateInput()).to.eq('2000-12-31', 'Expected actionDate value to be equals to 2000-12-31');

    await historicalUpdatePage.save();
    expect(await historicalUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await historicalComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Historical', async () => {
    const nbButtonsBeforeDelete = await historicalComponentsPage.countDeleteButtons();
    await historicalComponentsPage.clickOnLastDeleteButton();

    historicalDeleteDialog = new HistoricalDeleteDialog();
    expect(await historicalDeleteDialog.getDialogTitle()).to.eq('gatewayApp.historicalHistorical.delete.question');
    await historicalDeleteDialog.clickOnConfirmButton();

    expect(await historicalComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
