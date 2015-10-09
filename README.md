# hystrix4s


TestServer 와 Rest4sReqServer를 import poroject 하신 뒤
gradle dependancy update 해주세요.

TestServer 가 일정 시점 이후 장애를 일으키는 서버.
Rest4sReqServer 가 일반 HttpClient 및  Hystrix Client 입니다.

Rest4sReqServer 에 hystrix 소스를 포함시켰습니다. (com.netflix.hystrix)
직접 수정해가면서 해보시면 됩니다. (hystrix client는 반드시 java 1.7 로 해주세요, 1.8말고)

