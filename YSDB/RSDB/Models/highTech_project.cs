using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 高新技术关联RD
    /// </summary>
    public class highTech_project : tablebase
    {
        [Key]
        public int id { get; set; }
        public int companyId { get; set; }
        /// <summary>
        /// 研发项目RD
        /// </summary>
        public int projectId { get; set; }
        public int highTechId { get; set; }

    }
}
