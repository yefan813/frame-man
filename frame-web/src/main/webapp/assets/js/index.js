angular.module('app', [])
    .controller('dataCtrl', ['$scope', function($scope) {
        $scope.matchDetails = {
            "createTime": infos.data.createTime || "",
            "gamePlace": infos.data.gamePlace ||"",
            "homeTeam": infos.data.homeTeamData,
            "guestTeam": infos.data.guestTeamData
        };
        
        $scope.matchDetails.homeTeamReBound = 0;
        $scope.matchDetails.homeTeamFaqiu = 0;
        $scope.matchDetails.homeTeamTWOPoint = 0;
        $scope.matchDetails.homeTeamThreePoint = 0;
        
        for(var index in $scope.matchDetails.homeTeam.teamPlayerData){
        	$scope.matchDetails.homeTeamReBound += $scope.matchDetails.homeTeam.teamPlayerData[index].rebound;
        	$scope.matchDetails.homeTeamFaqiu += $scope.matchDetails.homeTeam.teamPlayerData[index].freeThrow;
        	$scope.matchDetails.homeTeamTWOPoint += $scope.matchDetails.homeTeam.teamPlayerData[index].twoPoint;
        	$scope.matchDetails.homeTeamThreePoint += $scope.matchDetails.homeTeam.teamPlayerData[index].threePoint;
        }
        
        
        $scope.matchDetails.guestTeamReBound = 0;
        $scope.matchDetails.guestTeamFaqiu = 0;
        $scope.matchDetails.guestTeamTWOPoint = 0;
        $scope.matchDetails.guestTeamThreePoint = 0;
        
        for(var index in $scope.matchDetails.guestTeam.teamPlayerData){
        	$scope.matchDetails.guestTeamReBound += $scope.matchDetails.guestTeam.teamPlayerData[index].rebound;
        	$scope.matchDetails.guestTeamFaqiu += $scope.matchDetails.guestTeam.teamPlayerData[index].freeThrow;
        	$scope.matchDetails.guestTeamTWOPoint += $scope.matchDetails.guestTeam.teamPlayerData[index].twoPoint;
        	$scope.matchDetails.guestTeamThreePoint += $scope.matchDetails.guestTeam.teamPlayerData[index].threePoint;
        }
    }]);