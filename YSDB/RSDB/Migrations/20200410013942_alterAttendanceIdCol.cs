using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class alterAttendanceIdCol : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<int>(
                name: "attendanceDataId",
                table: "p_attendance",
                nullable: true,
                oldClrType: typeof(int));
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<int>(
                name: "attendanceDataId",
                table: "p_attendance",
                nullable: false,
                oldClrType: typeof(int),
                oldNullable: true);
        }
    }
}
