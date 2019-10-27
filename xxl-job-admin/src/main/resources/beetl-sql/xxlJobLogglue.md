removeOld
===
    DELETE FROM xxl_job_logglue
		WHERE id NOT in(
            SELECT id FROM (
                SELECT id FROM xxl_job_logglue
                WHERE job_id = #jobId#
                ORDER BY update_time desc
                 @if(db.type() == "mysql"){
                    LIMIT 0, #limit#
                @}
            ) t1
            @if(db.type() == "oracle"){
                where rownum <= #limit#
            @}
		) AND job_id = #jobId#
		