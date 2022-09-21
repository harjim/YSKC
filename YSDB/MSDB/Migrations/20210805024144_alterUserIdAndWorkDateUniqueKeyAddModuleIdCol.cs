using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class alterUserIdAndWorkDateUniqueKeyAddModuleIdCol : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_user_audit_detail_userId_workDate",
                table: "user_audit_detail");

            migrationBuilder.CreateIndex(
                name: "IX_user_audit_detail_userId_workDate_moduleId",
                table: "user_audit_detail",
                columns: new[] { "userId", "workDate", "moduleId" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_user_audit_detail_userId_workDate_moduleId",
                table: "user_audit_detail");

            migrationBuilder.CreateIndex(
                name: "IX_user_audit_detail_userId_workDate",
                table: "user_audit_detail",
                columns: new[] { "userId", "workDate" },
                unique: true);
        }
    }
}
