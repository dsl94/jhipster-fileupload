(function() {
    'use strict';

    angular
        .module('fileuploadApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('candidate', {
            parent: 'entity',
            url: '/candidate',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Candidates'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/candidate/candidates.html',
                    controller: 'CandidateController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('candidate-detail', {
            parent: 'entity',
            url: '/candidate/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Candidate'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/candidate/candidate-detail.html',
                    controller: 'CandidateDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Candidate', function($stateParams, Candidate) {
                    return Candidate.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'candidate',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('candidate-detail.edit', {
            parent: 'candidate-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/candidate/candidate-dialog.html',
                    controller: 'CandidateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Candidate', function(Candidate) {
                            return Candidate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('candidate.new', {
            parent: 'candidate',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/candidate/candidate-dialog.html',
                    controller: 'CandidateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                cvFile: null,
                                cvFileContentType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('candidate', null, { reload: 'candidate' });
                }, function() {
                    $state.go('candidate');
                });
            }]
        })
        .state('candidate.edit', {
            parent: 'candidate',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/candidate/candidate-dialog.html',
                    controller: 'CandidateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Candidate', function(Candidate) {
                            return Candidate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('candidate', null, { reload: 'candidate' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('candidate.delete', {
            parent: 'candidate',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/candidate/candidate-delete-dialog.html',
                    controller: 'CandidateDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Candidate', function(Candidate) {
                            return Candidate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('candidate', null, { reload: 'candidate' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
