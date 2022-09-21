using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class addRoleDataUniqueRoleIdAndFunctionId : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateIndex(
                name: "IX_app_role_data_roleId_functionId",
                table: "app_role_data",
                columns: new[] { "roleId", "functionId" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_app_role_data_roleId_functionId",
                table: "app_role_data");
        }
    }
}
