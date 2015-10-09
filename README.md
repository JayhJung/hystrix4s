# hystrix4s


TestServer 와 Rest4sReqServer를 import poroject 하신 뒤
gradle dependancy update 해주세요.

TestServer 가 일정 시점 이후 장애를 일으키는 서버. 8080


Rest4sReqServer 가 일반 HttpClient 및  Hystrix Client 입니다. vm 옵션에서 8090 으로 올려주세요(-Dserver.port=8090)

Rest4sReqServer 에 hystrix 소스를 포함시켰습니다. (com.netflix.hystrix)
직접 수정해가면서 해보시면 됩니다. (hystrix client는 반드시 java 1.7 로 해주세요, 1.8말고)


HystrixCiruitBreaker.java 에 sysout 으로 상태 트래킹 할 수 있도록 해 놓았어요
HystrixCiruitBreaker 의 동작으로 보니
Circuit이 계속 열려있다가, HystrixCiruitBreaker.allowSingleTest() 가 false에서 true로 바뀌는 시점에, request가 나가서 한번 체크하고 돌아옵니다.
이 부분에 체크로직을 추가하고 false를 리턴하면 내부 request는 안나갈테니.., healthCheck결과에 따라자체적으로 circuit을 제어해주면 될것 같아요(자체 healthcheck결과 OK 이면 request 허용하고:return true, check결과 서버가Not OK 이면, request를 그냥먹는걸로:return false;) 

healthcheck url 은 HystrixCommandProperties 에 하드코딩으로 해놓았고,
requestHealthCheck() 쪽 쓰다가... 일단 커밋해놓을께요... 
httpClient자체 속성으로 timeout등을 설정하면 될듯 합니다.

