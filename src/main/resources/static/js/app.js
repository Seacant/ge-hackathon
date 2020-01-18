const URL = '/';

$(document).ready(() => {
  $('#approve, #spam, #defer').click((button) => {
    let active_email = $('#email-queue .card.active')
    let active_email_data = active_email.data('json')

    let is_spam = null;
    switch ($(button.target).attr('id')) {
      case 'approve':
        is_spam = false
        break
      case 'spam':
        is_spam = true
        break
      case 'defer':
        is_spam = null
    }

    // Submit the current active email with the requested is_spam state
    fetch(
      URL + 'email/' + active_email_data.emailId,
      {
        method: 'PUT',
        body: JSON.stringify({ isSpam: is_spam }),
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        credentials: 'same-origin'
      }
    )
    remove_active_email();
    load_next_email();
    active_next_email_in_queue();
  });

  $(document).keypress((e) => {
    let c = String.fromCharCode(e.which);

    if(c == 'a') { $('#approve').click() }
    if(c == 's') { $('#spam').click() }
    if(c == 'd') { $('#defer').click() }
  })

  // Bootstrap the user with a few emails
  for (let i = 0; i < 10; i++) {
    load_next_email()
      .then(active_next_email_in_queue);
  }
})

function remove_active_email() {
  $('#email-queue .card.active').remove()
}

function active_next_email_in_queue() {
  const card = $($('#email-queue .card').removeClass('active').get(0)).addClass('active')
  const data = card.data('json')

  $('#email-info').html(`
    <table>
      <tr>
        <td>Sender: ${data.sender}</td>
        <td>Recipient: ${data.recipient}</td>
      </tr>
      <tr>
        <td>Subject: ${data.subject}</td>
        <td>Sent Date: ${data.sendDate}</td>
      </tr>
      <tr>
        <td>Attachment: ${data.attachment}</td>
      </tr>
    </table>
  `)

  $('#email-body').html(`${data.body}`);
}

function load_next_email() {
  return fetch(
    URL + 'email/next',
    {
      method: 'PUT',
      headers: {
        'Accept': 'application/json'
      },
      credentials: 'same-origin'
    }
  )
    .then((res) => res.json())
    .then(data => {
      add_email_to_queue(data);
    })
}

function add_email_to_queue(emailInfo) {
  $('#email-queue').append(`
    <div class="card" id='email-${emailInfo.emailId}' data-json='${JSON.stringify(emailInfo)}'>
      <div class="card-panel">
        <span class="column">${emailInfo.subject}</span>
      </div>
    </div>
  `)
}