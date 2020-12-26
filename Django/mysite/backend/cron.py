#open close

@csrf_exempt
def star(request):
    if request.method == 'GET':
        query_set=Cafe.objects.all().values_list('id', 'start_time','end_time')
        query_setv = Cafe.objects.values()
        now = datetime.datetime(datetime.datetime.now().year,datetime.datetime.now().month,datetime.datetime.now().day,datetime.datetime.now().hour,datetime.datetime.now().minute,datetime.datetime.now().second)
        serializer = CafeSerializer(query_set, many = True)
        a=query_set
        j=0
        for i in query_set:
            start = datetime.datetime(datetime.datetime.now().year,datetime.datetime.now().month,datetime.datetime.now().day,a[j][1].hour,a[j][1].minute,a[j][1].second)
            end = datetime.datetime(datetime.datetime.now().year,datetime.datetime.now().month,datetime.datetime.now().day,a[j][2].hour,a[j][2].minute,a[j][2].second)
            print(start)
            print(end)
            print(a[j][0])
            if start < end:
                if now >= start:
                    if now < end:
                        post_instance = Cafe.objects.get(id=a[j][0])
                        post_instance.business = 'True'
                        post_instance.save()
                    elif now > end:
                        post_instance = Cafe.objects.get(id=a[j][0])
                        post_instance.business = 'False'
                        post_instance.save()

            elif start > end:
                if now >= start:
                    if now > end:
                        post_instance = Cafe.objects.get(id=a[j][0])
                        post_instance.business = 'True'
                        post_instance.save()
                    elif now < end:
                        post_instance = Cafe.objects.get(id=a[j][0])
                        post_instance.business = 'False'
                        post_instance.save()
                elif now < start:
                    if now < end:
                        post_instance = Cafe.objects.get(id=a[j][0])
                        post_instance.business = 'True'
                        post_instance.save()
                    elif now > end:
                        post_instance = Cafe.objects.get(id=a[j][0])
                        post_instance.business = 'False'
                        post_instance.save()

            else:
                post_instance = Cafe.objects.get(id=a[j][0])
                post_instance.business = 'False'
                post_instance.save()
            j=j+1

    return HttpResponse(query_setv)    


