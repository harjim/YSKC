using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
  public class p_change:tablebase
  {
    [Key]
    public int id { get; set; }
    /// <summary>
    /// 变更类型, 1: 项目名称, 2: 负责人
    /// </summary>
    public int changeType { get; set; }
    /// <summary>
    /// 变更时间
    /// </summary>
    public DateTime changeTime { get; set; }
    /// <summary>
    /// 变更内容,如果类型为项目名称,则content为变更后的项目名,如果类型为负责人,则content为变更后的负责人的ID,姓名json格式,形如: {enumber:xxx,ename:xxxx}
    /// </summary>
    [Required, MaxLength(200)]
    public string content { get; set; }

    ///<summary>
    /// 备注
    /// </summary>
    [MaxLength(200)]
    public string remark { get; set; }
    ///<summary>
    /// 公司id
    /// </summary>
    public int companyId { get; set; }
    ///<summary>
    /// 项目id
    /// </summary>
    public int projectId { get; set; }

  }
}