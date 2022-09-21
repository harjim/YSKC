using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    public class employee : tablebase
    {
        [Key]
        public int id { get; set; }
        public int companyId { get; set; }
        /// <summary>
        /// 员工编号/标识
        /// </summary>
        [Required, MaxLength(30)]
        public String enumber { get; set; }
        /// <summary>
        /// 姓名
        /// </summary>
        [Required, MaxLength(20)]
        public String ename { get; set; }
        /// <summary>
        /// 部门id
        /// </summary>
        public int? deptId { get; set; }
        /// <summary>
        /// 部门
        /// </summary>
        [MaxLength(100)]
        public string deptName { get; set; }
        /// <summary>
        /// 职位
        /// </summary>
        [Required, MaxLength(20)]
        public String position { get; set; }
        /// <summary>
        /// 身份证号
        /// </summary>
        [MaxLength(20)]
        public String idNumber { get; set; }
        /// <summary>
        /// 入职时间
        /// </summary>
        public DateTime? edate { get; set; }
        /// <summary>
        /// 学历， 0:无，1：高中及以下，2：中专，3：大专，4：本科 5：硕士，6：博士
        /// </summary>
        public int eduLevel { get; set; }

        [MaxLength(300)]
        public String remark { get; set; }

        /// <summary>
        /// 职称
        /// </summary>
        [MaxLength(50)]
        public string title { get; set; }

        /// <summary>
        /// 性别
        /// </summary>
        public int gender { get; set; }
        /// <summary>
        /// 离职日期
        /// </summary>
        public DateTime? leaveDate { get; set; }

        /// <summary>
        /// 专业
        /// </summary>
        [MaxLength(100)]
        public string specialities { get; set; }
        /// <summary>
        /// 生日
        /// </summary>
        public DateTime? birthday { get; set; }
        /// <summary>
        /// 禁用研发考勤
        /// </summary>
        public bool disabledAtt { get; set; }
        ///<summary>
        ///角色类型：0：普通，1：项目统筹
        ///</summary>
        public int roleType { get; set; }
        ///<summary>
        ///亲笔签名地址
        ///</summary>
        [MaxLength(200)]
        public string autographUrl { get; set; }

        [MaxLength(2000)]
        public string data { get; set; }

    }
}
