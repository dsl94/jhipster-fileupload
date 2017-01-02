(function() {
    'use strict';
    angular
        .module('fileuploadApp')
        .factory('Candidate', Candidate);

    Candidate.$inject = ['$resource'];

    function Candidate ($resource) {
        var resourceUrl =  'api/candidates/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
