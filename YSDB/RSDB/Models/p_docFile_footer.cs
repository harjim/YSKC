using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 过程文件页脚设置
    /// </summary>
    public class p_docFile_footer : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 项目id
        /// </summary>
        public int projectId { get; set; }
        /// <summary>
        /// 编制人员工号
        /// </summary>
        public String toCompileEnumber { get; set; }
        /// <summary>
        /// 审核人员工号
        /// </summary>
        public String auditEnumber { get; set; }
        /// <summary>
        /// 批准人员工号
        /// </summary>
        public String approvalEnumber { get; set; }

        public int companyId { get; set; }

        /// <summary>
        /// 年份
        /// </summary>
        public int year { get; set; }
    }
}
