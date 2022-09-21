using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    public class p_audit_docFile
    {
        [Key]
        public int id { get; set; }
        public int companyId { get; set; }
        public int projectId { get; set; }
        ///<summary>
        ///过程文档Id
        ///</summary>
        public int docFileId { get; set; }
        public int moduleId { get; set; }
        /// <summary>
        /// 0进行中 1通过 2驳回 3激活 4提交 5未提交 6失败 7提交失败
        /// </summary>
        public int status { get; set; }
        public int msCreatorId { get; set; }
        public int msLastUpdatorId { get; set; }
        public DateTime createTime { get; set; }
        public DateTime lastUpdateTime { get; set; }
        [MaxLength(2000)]
        public string suggestion { get; set; }
    }
}