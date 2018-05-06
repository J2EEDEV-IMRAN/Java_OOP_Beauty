List<String> actScheduleAttachmentList = new ArrayList<>();
        List<MultipartFile> multipartFileList =new ArrayList<>();
        for (MultipartFile actScheduleAttachment : actScheduleAttachmentFile) {
            actScheduleAttachmentList.add(uploadFile(actScheduleAttachment));
            multipartFileList.add(actScheduleAttachment);
        }


        if (act.getActScheduleList() != null) {

            Integer actScheduleIndex = new Integer(0);
            for (ActScheduleForAct actSchedule : act.getActScheduleList()
                    ) {
                List<ActScheduleForAct> previousActs = actScheduleForActRepository.findAllByActId(actOld.getId());

                if (previousActs.size() != 0) {
                    for (ActScheduleForAct actScheduleForAct : previousActs)
                    {
                        if ( actSchedule.getId() != null && actSchedule.getId().equals(actScheduleForAct.getId()) && !multipartFileList.get(actScheduleIndex).getContentType().equals("application/octet-stream"))
                        {
                            actSchedule.setAttachment(actScheduleAttachmentList.get(actScheduleIndex));
                        }
                        else if(actSchedule.getId() != null && actSchedule.getId().equals(actScheduleForAct.getId()) && multipartFileList.get(actScheduleIndex).getContentType().equals("application/octet-stream"))
                        {
                            actSchedule.setAttachment(actScheduleForAct.getAttachment());
                        }
                        else if (actSchedule.getId() == null)
                        {
                            actSchedule.setAttachment(actScheduleAttachmentList.get(actScheduleIndex));
                        }
                    }
                    if (actScheduleAttachmentList.get(actScheduleIndex) != null) {
                        actSchedule.setAttachment(actScheduleAttachmentList.get(actScheduleIndex));
                    }

                } else {
                    if (actScheduleAttachmentList.get(actScheduleIndex) != null) {
                        actSchedule.setAttachment(actScheduleAttachmentList.get(actScheduleIndex));
                    }
                }

            /*    if (actScheduleAttachmentList.get(actScheduleIndex) != null) {
                    actSchedule.setAttachment(actScheduleAttachmentList.get(actScheduleIndex));
                }*/
                if (actSchedule.getAct() == null) {
                    actSchedule.setAct(act);
                }
                actSchedule.setCreatedBy(actOld.getCreatedBy());
                actSchedule.setCreatedAt(actOld.getCreatedAt());
                actSchedule.setUpdatedBy(getUsername());
                actSchedule.setUpdatedAt(new Date());
                actScheduleIndex++;
            }
        }
