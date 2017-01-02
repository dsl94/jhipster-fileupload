(function() {
    'use strict';

    angular
        .module('fileuploadApp')
        .controller('CandidateDetailController', CandidateDetailController);

    CandidateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Candidate'];

    function CandidateDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Candidate) {
        var vm = this;

        vm.candidate = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('fileuploadApp:candidateUpdate', function(event, result) {
            vm.candidate = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
