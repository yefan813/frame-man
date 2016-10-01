angular.module('app', [])
    .controller('dataCtrl', ['$scope', function($scope) {
        $scope.matchDetails = {
            "createTime": infos.data.createTime || "空",
            "gamePlace": infos.data.gamePlace ||"空",
            "homeTeam": infos.data.homeTeamData,
            "guestTeam": infos.data.guestTeamData
        };
    }]);