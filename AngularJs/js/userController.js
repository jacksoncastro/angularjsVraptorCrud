var mvcApp = angular.module('userApp', []);
mvcApp.controller('userController', function mvcController($scope, $http, $window) {

    var base_url = 'http://localhost:8080/vraptorCrud';

    var reset = function(){
        $scope.user = {id : null, login : "", name : "", password: ""};
    };

    $scope.initUsers = function() {
        $http.get(base_url + '/user', $scope.user).success(function(data) {
            $scope.users = data;
        });
    };

    $scope.postUser = function () {
        $http.post(base_url + '/user', $scope.user).success(function(data){
            $scope.users.unshift(data);
            reset();
        });
    };

    //This function adds the user to the form
    $scope.edit = function(user){
        $scope.user = user;
    };

    $scope.deleteUser = function(user){
        var confirm = $window.confirm('Remove user ' + user.login + '?');
        if(confirm) {
            var url = base_url + '/user/' + user.id;
            $http.delete(url).success(function(data) {
                //get position of the object in the collection
                var index = $scope.users.indexOf(user);
                //function to remove user
                $scope.users.splice(index, 1);
                reset();
            });
        }
    };

    //Essa será executada no click do botão edit ("putUser")
    $scope.putUser = function (user) {
        //converting the user in json
        var url = base_url + '/user';
        var index = $scope.users.indexOf(user);
        $scope.users.splice(index, 1);
        $http.put(url, user).success(function(data) {
            $scope.users.unshift(data);
            reset();
        });
    };
});