using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 过程文档试制
    /// </summary>
    public class p_docFile_trial
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 文档数据id（p_docFile）
        /// </summary>
        public int pdocFileId { get; set; }

        public int trialId { get; set; }

        public int companyId { get; set; }

        public DateTime createTime { get; set; }

        public int creatorId { get; set; }

        public int msCreatorId { get; set; }

    }
}
