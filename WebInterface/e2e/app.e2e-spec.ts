import { BluetoothPage } from './app.po';

describe('bluetooth App', function() {
  let page: BluetoothPage;

  beforeEach(() => {
    page = new BluetoothPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
