using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class addUniqueToFlowInstanceReport : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateIndex(
                name: "IX_flowInstance_report_rsProjectId_moduleId",
                table: "flowInstance_report",
                columns: new[] { "rsProjectId", "moduleId" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_flowInstance_report_rsProjectId_moduleId",
                table: "flowInstance_report");
        }
    }
}
