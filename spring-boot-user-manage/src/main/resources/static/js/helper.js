var Helper = {};

Helper.createDataTable = function(selector) {
  $(selector)
    .dataTable({
      'language': {
        'emptyTable': 'データが登録されていません。',
        'info': '_TOTAL_ 件中  _START_ 件から _END_ 件までを表示',
        'infoEmpty': '',
        'infoFiltered': '(_MAX_ 件からの絞込み表示)',
        'infoPostFix': '',
        'thousands': ',',
        'lengthMenu': '1ページあたりの表示件数 _MENU_',
        'loadingRecords': 'Loading...',
        'processing': 'Processing...',
        'search': '検索:',
        'zeroRecords': '該当するデータが見つかりませんでした。',
        'paginate': {
          'first': '先頭',
          'last': '末尾',
          'next': '次へ',
          'previous': '前へ'
        }
      }
    })
    .show();
};
