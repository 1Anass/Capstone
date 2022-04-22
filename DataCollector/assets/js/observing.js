var Rx = require("rxjs");
var Op = require("rxjs/operators");
var Stomp = require("webstomp-client");




const afterClick = (() => {


  // sock js object with corresponding url
  var socket = new SockJS('http://localhost:8080/connect');

  //stomp client object created over sock js object to support sockjs fallback options
  var stompClient = Stomp.over(socket);

  //establishing connection
  stompClient.connect({}, function (frame) {

    console.log("connection " + frame);

    console.log("Connected to server");

    //subscribing to /topic/assistance where server messages will be published
    stompClient.subscribe('/topic/assistance', function (frame) {
      console.log("Message received Back");
      console.log(frame.body);

    });
  });



  window.addEventListener('beforeunload', function () {
    stompClient.disconnect(function () {
      alert("See you next time!");
    })
  });

  //setTimeout(() => {  console.log("Waited for connection to be established"); }, 5000);

  //observable will be created whenever a user makes a click
  //scroll try 1

  // fromEvent(window, 'scroll').pipe(
  //   debounceTime(20), 
  //   distinctUntilChanged(), 
  //   map(v => sp()), 
  //   pairwise(), 
  //   switchMap(p => {
  //   const y1 = p[0][1]
  //   const y2 = p[1][1]
  //   console.log(str(y1-y2))
  //   return y1 - y2 > 0 ? of(false) : of(true)
  // }
  // )).subscribe(()=>console.log(v));



  //click event observable
  var click = Rx.fromEvent(document, 'click', true);

  // expressing the processing to be made before sending clicks
  let clickProcessed = click.pipe(
    Op.map((event) => {

      //get path of page
      var path = window.location.pathname;

      //get current timestamp
      const currentDate = new Date();
      var timestamp = currentDate.getTime();

      //JSON format of user click event
      var clickJSON = {
        eventType: "click", "path": path, "timestamp": timestamp, "tagName": event.relatedTarget, "targetId": event.target.id, "targetClass": event.target.className,
        "pageX": event.pageX, "pageY": event.pageY
      }

      //creating message to be sent through the web socket
      const message = JSON.stringify(clickJSON);

      //printing json object to make sure it is correct, to be removed later
      console.log(JSON.stringify(clickJSON));

      //send message to server through websocket connection to /app/events destination
      stompClient.send("/app/events", message, {});



    })
  ).subscribe()

  //---------------------------------- reading scroll events ---------------------------------

  const getScrollWidth = () => {
    const doc = document.documentElement;
    const winScroll = doc.scrollTop;
    const height = doc.scrollHeight - doc.clientHeight;
    return (winScroll / height) * 100;
  };

  Rx.fromEvent(document, 'scroll')
    .pipe(
      Op.throttleTime(200),
      Op.map((event) => {

        //get path of page
        var path = window.location.pathname;

        //get current timestamp
        const currentDate = new Date();
        var timestamp = currentDate.getTime();

        var scrollHeight = getScrollWidth();

        //JSON format of user click event
        var scrollJSON = {
          "eventType": "scroll", "path": path, "timestamp": timestamp, "scrollHeight": scrollHeight
        }

        const message = JSON.stringify(scrollJSON);

        console.log(JSON.stringify(scrollJSON));

        //send message to server through websocket connection to /app/events destination
        stompClient.send("/app/events", message, {});

      }

      ))
    .subscribe();


  //------------------------------------ handling mouseover events -----------------------------
  Rx.fromEvent(window, 'mouseover').pipe(
    Op.throttleTime(200), 
    Op.map((event) => 
    {
      
        //get path of page
        var path = window.location.pathname;

        //get current timestamp
        const currentDate = new Date();
        var timestamp = currentDate.getTime();

        //JSON format of user click event
        var mouseoverJSON = {
          "eventType": "mouseover", "path": path, "timestamp": timestamp
        }

        const message = JSON.stringify(mouseoverJSON);

        console.log(JSON.stringify(mouseoverJSON));

        //send message to server through websocket connection to /app/events destination
        stompClient.send("/app/events", message, {});
    }
    )).subscribe();

//------------------------------------------------- Handle OpenPageEvents -----------------------------------

Rx.fromEvent(window, 'load').pipe(
  Op.delay(2000), 
  Op.map((event) => 
  {    
      //get path of page
      var path = window.location.pathname;

      // get current timestamp
      const currentDate = new Date();
      var timestamp = currentDate.getTime();

      // //JSON format of user click event
      var pageOpenJSON = {
      "eventType": "pageOpen", "path": path, "timestamp": timestamp
      }

      const message = JSON.stringify(pageOpenJSON);

      console.log(JSON.stringify(pageOpenJSON));

      //send message to server through websocket connection to /app/events destination
      stompClient.send("/app/events", message, {});
  }
  )).subscribe();

  /*stompClient.disconnect(function() {
    alert("See you next time!");
  });;*/



})()
  ;



